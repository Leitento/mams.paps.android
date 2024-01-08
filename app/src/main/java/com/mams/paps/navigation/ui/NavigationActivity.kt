package com.mams.paps.navigation.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mams.paps.NavMainDirections
import com.mams.paps.R
import com.mams.paps.databinding.ActivityNavigationBinding
import com.yandex.mapkit.MapKitFactory

class NavigationActivity : AppCompatActivity(R.layout.activity_navigation) {

    private val binding by viewBinding(ActivityNavigationBinding::bind)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        MapKitFactory.initialize(this)
        super.onCreate(savedInstanceState)

        with(binding) {
            navController = fragmentContainer.getFragment<NavHostFragment>().navController
            bottomNavigationView.selectedItemId = R.id.navigation_map
            bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_home -> {
                        val action = NavMainDirections.actionOpenMainScreen()
                        navController.navigate(action)
                        false
                    }
                    else -> NavigationUI.onNavDestinationSelected(it, navController)
                }
            }
        }
    }
}
