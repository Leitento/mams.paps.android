package com.mams.paps.navigation.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.snackbar.Snackbar
import com.mams.paps.R
import com.mams.paps.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.Padding
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class MapFragment : Fragment(R.layout.fragment_map) {

    private val binding by viewBinding(FragmentMapBinding::bind)
    private var map: Map? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var moveCameraJob: Job? = null

    private val cameraListener = CameraListener { _, cameraPosition, cameraUpdateReason, finished ->
        if (cameraUpdateReason == CameraUpdateReason.GESTURES) {
            cancelMoveCameraToMe()
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
        ViewCompat.setOnApplyWindowInsetsListener(binding.overlay) { overlay, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            overlay.updatePadding(
                top = insets.top
            )

            windowInsets
        }

        setBottomSheetFragment(CategoriesFragment())

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
    }

    override fun onDestroyView() {
        map?.removeCameraListener(cameraListener)
        map = null

        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
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
            runCatching {
                binding.myLocationProgress.show()
                getLastLocation()?.let {
                    moveCamera(Point(it.latitude, it.longitude), DEFAULT_ZOOM, animate = animate)
                }
                getCurrentLocation()?.let {
                    moveCamera(Point(it.latitude, it.longitude), DEFAULT_ZOOM, animate = animate)
                }
                binding.myLocationProgress.hide()
            }.onFailure {
                if (it !is CancellationException) {
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
    }
}