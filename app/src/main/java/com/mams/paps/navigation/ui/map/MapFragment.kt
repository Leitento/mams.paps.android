package com.mams.paps.navigation.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.search.SearchView
import com.google.android.material.snackbar.Snackbar
import com.mams.paps.R
import com.mams.paps.databinding.FragmentMapBinding
import com.mams.paps.navigation.model.MapCategory
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.Padding
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SuggestItem
import com.yandex.mapkit.search.SuggestOptions
import com.yandex.mapkit.search.SuggestSession
import com.yandex.mapkit.search.SuggestType
import com.yandex.runtime.Error
import com.yandex.runtime.network.NetworkError
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

@FlowPreview
class MapFragment : Fragment(R.layout.fragment_map) {

    private val viewModel: MapViewModel by navGraphViewModels(R.id.navigation_map)
    private val binding by viewBinding(FragmentMapBinding::bind)
    private var map: Map? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var moveCameraJob: Job? = null
    private var geoSuggestAdapter: GeoSuggestAdapter? = null

    private val searchManager = SearchFactory.getInstance()
        .createSearchManager(SearchManagerType.ONLINE)
    private val suggestOptions = SuggestOptions().apply {
        suggestTypes = SuggestType.GEO.value or SuggestType.BIZ.value
        suggestWords = false
    }
    private val suggestSession = searchManager.createSuggestSession()

    private val cameraListener = CameraListener { _, cameraPosition, cameraUpdateReason, finished ->
        if (cameraUpdateReason == CameraUpdateReason.GESTURES) {
            cancelMoveCameraToMe()
        }
    }

    private val suggestListener = object : SuggestSession.SuggestListener {
        override fun onResponse(items: MutableList<SuggestItem>) {
            geoSuggestAdapter?.submitList(items) {
                binding.suggestList.scrollToPosition(0)
            }
        }

        override fun onError(error: Error) {
            if (error is NetworkError) {
                Snackbar.make(
                    requireView(), R.string.error_connection, Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private val suggestClickListener: GeoSuggestClickListener = { suggestItem ->
        with(binding) {
            if (suggestItem.action == SuggestItem.Action.SUBSTITUTE) {
                searchView.editText.setText(suggestItem.displayText)
            } else {
                moveCamera(target = suggestItem.center)
                searchView.hide()
            }
        }
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.any { it.value }) {
            moveCameraToMe()
        } else {
            Snackbar.make(
                requireView(), R.string.location_permission_denied, Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ViewCompat.setOnApplyWindowInsetsListener(binding.searchBar) { searchBar, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            searchBar.updateLayoutParams<MarginLayoutParams> {
                topMargin = insets.top + resources.getDimensionPixelOffset(R.dimen.common_spacing)
            }

            windowInsets
        }

        with(binding) {
            map = mapView.mapWindow.map.apply {
                moveCamera(DEFAULT_POINT)
                logo.setAlignment(
                    Alignment(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM)
                )
                logo.setPadding(
                    Padding(
                        0,
                        resources.getDimension(R.dimen.bottom_sheet_peek_height).toInt()
                    )
                )
                addCameraListener(cameraListener)
                moveCameraToMe()
            }

            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchView.hide()
                false
            }

            searchView.addTransitionListener { _, _, newState ->
                if (newState == SearchView.TransitionState.HIDDEN) {
                    suggestSession.reset()
                }
            }

            searchView.editText.doOnTextChanged { text, _, _, _ ->
                viewModel.setSearchQuery(text.toString())
            }

            geoSuggestAdapter = GeoSuggestAdapter(suggestClickListener)
            suggestList.adapter = geoSuggestAdapter

            buttonZoomIn.setOnClickListener {
                changeZoom(true)
            }
            buttonZoomOut.setOnClickListener {
                changeZoom(false)
            }
            buttonMyLocation.setOnClickListener {
                moveCameraToMe(true)
            }
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        setBottomSheetFragment(MapCategoriesFragment())

        childFragmentManager.setFragmentResultListener(
            MapCategoriesFragment.REQUEST_KEY_CATEGORY,
            viewLifecycleOwner
        ) { _, result ->
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            val categoryName = result.getString(MapCategoriesFragment.RESULT_KEY_CATEGORY_NAME)
            val category = MapCategory.fromName(categoryName)

            // TODO: Open the filter screen
            viewModel.setCategory(category)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.searchQuery
                        .debounce(SUGGEST_DEBOUNCE_TIMEOUT)
                        .collect {
                            suggest(it)
                        }
                }
            }
        }
    }

    override fun onDestroyView() {
        map?.removeCameraListener(cameraListener)
        map = null
        geoSuggestAdapter = null

        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun setBottomSheetFragment(fragment: Fragment) {
        childFragmentManager.commit {
            replace(R.id.bottom_sheet_fragment_container, fragment)
        }
    }

    private fun changeZoom(zoomIn: Boolean) {
        val zoomOffset = if (zoomIn) ZOOM_STEP else -ZOOM_STEP

        map?.let {
            val position = it.cameraPosition
            it.move(
                CameraPosition(
                    position.target,
                    position.zoom + zoomOffset,
                    position.azimuth,
                    position.tilt
                ),
                ZOOM_ANIMATION,
                null
            )
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLastLocation(): Location? {
        if (!hasLocationPermission()) {
            requestLocationPermissions()
            return null
        }

        return fusedLocationClient.lastLocation.await()
    }

    @SuppressLint("MissingPermission")
    private suspend fun getCurrentLocation(): Location? {
        if (!hasLocationPermission()) {
            requestLocationPermissions()
            return null
        }

        val cancellationToken = CancellationTokenSource()
        return fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationToken.token
        ).await(cancellationToken)
    }

    private fun moveCameraToMe(animate: Boolean = true) {
        moveCameraJob?.cancel()
        moveCameraJob = viewLifecycleOwner.lifecycleScope.launch {
            try {
                binding.myLocationProgress.show()
                getLastLocation()?.let {
                    moveCamera(Point(it.latitude, it.longitude), DEFAULT_ZOOM, animate = animate)
                }
                getCurrentLocation()?.let {
                    moveCamera(Point(it.latitude, it.longitude), DEFAULT_ZOOM, animate = animate)
                }
                binding.myLocationProgress.hide()
            } catch (e: Exception) {
                if (e !is CancellationException) {
                    Snackbar.make(
                        requireView(), R.string.error_my_location, Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun cancelMoveCameraToMe() {
        moveCameraJob?.cancel()
        moveCameraJob = null
        binding.myLocationProgress.hide()
    }

    private fun moveCamera(
        target: Point? = null,
        zoom: Float? = null,
        azimuth: Float? = null,
        tilt: Float? = null,
        animate: Boolean = true,
        callback: Map.CameraCallback? = null
    ) {
        val map = map ?: return

        map.cameraPosition.run {
            val position = CameraPosition(
                target ?: this.target,
                zoom ?: this.zoom,
                azimuth ?: this.azimuth,
                tilt ?: this.tilt
            )
            if (animate) {
                map.move(position, MOVE_ANIMATION, callback)
            } else {
                map.move(position)
            }
        }
    }

    private fun suggest(text: String) = viewLifecycleOwner.lifecycleScope.launch {
        val map = map ?: return@launch

        try {
            getCurrentLocation()?.let {
                suggestOptions.userPosition = Point(it.latitude, it.longitude)
            }
            suggestSession.suggest(
                text,
                BoundingBox(map.visibleRegion.bottomLeft, map.visibleRegion.topRight),
                suggestOptions,
                suggestListener,
            )
        } catch (e: Exception) {
            Snackbar.make(requireView(), R.string.error_suggest, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun requestLocationPermissions() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun hasLocationPermission(): Boolean {
        val context = requireContext()
        val fineLocationPermission =
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocationPermission =
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)

        return (fineLocationPermission == PackageManager.PERMISSION_GRANTED
                || coarseLocationPermission == PackageManager.PERMISSION_GRANTED)
    }

    companion object {
        private val DEFAULT_POINT = Point(55.753546, 37.621204)
        private const val DEFAULT_ZOOM = 15f
        private const val ZOOM_STEP = 1f
        private val ZOOM_ANIMATION = Animation(Animation.Type.LINEAR, 0.25f)
        private val MOVE_ANIMATION = Animation(Animation.Type.SMOOTH, 0.5f)

        private const val SUGGEST_DEBOUNCE_TIMEOUT = 300L
    }
}
