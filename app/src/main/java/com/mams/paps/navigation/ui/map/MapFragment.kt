package com.mams.paps.navigation.ui.map

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mams.paps.R
import com.mams.paps.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.Padding
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map

class MapFragment : Fragment(R.layout.fragment_map) {

    private val binding by viewBinding(FragmentMapBinding::bind)
    private var map: Map? = null

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
                move(
                    CameraPosition(
                        Point(55.753546, 37.621204),
                        DEFAULT_ZOOM,
                        0f,
                        0f
                    )
                )
                logo.setAlignment(
                    Alignment(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM)
                )
                logo.setPadding(
                    Padding(
                        0,
                        resources.getDimension(R.dimen.bottom_sheet_peek_height).toInt()
                    )
                )
            }

            buttonZoomIn.setOnClickListener {
                changeZoom(true)
            }

            buttonZoomOut.setOnClickListener {
                changeZoom(false)
            }
        }
    }

    override fun onDestroyView() {
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

    private fun setBottomSheetFragment(fragment: Fragment) {
        childFragmentManager.commit {
            replace(R.id.bottom_sheet_fragment_container, fragment)
        }
    }

    companion object {
        private const val DEFAULT_ZOOM = 15f
        private const val ZOOM_STEP = 1f
        private val ZOOM_ANIMATION = Animation(Animation.Type.LINEAR, 0.25f)
    }
}
