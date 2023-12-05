package com.mams.paps.navigation.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mams.paps.R
import com.mams.paps.databinding.ActivityNavigationBinding
import com.yandex.mapkit.MapKitFactory

class NavigationActivity : AppCompatActivity(R.layout.activity_navigation) {

    private val binding by viewBinding(ActivityNavigationBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        super.onCreate(savedInstanceState)

        with(binding) {
            val navController = fragmentContainer.getFragment<NavHostFragment>().navController
            bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_map -> {
                        navController.navigate(R.id.map_fragment)
                        true
                    }
                    else -> false
                }
            }
        }

        MapKitFactory.initialize(this)
    }
}
