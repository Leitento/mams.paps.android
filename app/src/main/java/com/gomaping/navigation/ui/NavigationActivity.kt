package com.gomaping.navigation.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.NavMainDirections
import com.gomaping.R
import com.gomaping.databinding.ActivityNavigationBinding
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

            bottomNavigationView.setupWithNavController(navController)
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
            if (savedInstanceState == null) {
                val destinationId = intent.getIntExtra(ARG_NAVIGATE_TO, DEFAULT_DESTINATION_ID)
                bottomNavigationView.selectedItemId = destinationId
            }

            navController.addOnDestinationChangedListener { _, destanetion, _ ->
                when (destanetion.id) {
                    R.id.navigation_map, R.id.navigation_nearby, R.id.navigation_profile,
                    R.id.navigation_add,R.id.mapCategoriesFragment2, R.id.playgroundFilterFragment2
                    -> {
                        bottomNavigationView.visibility = View.VISIBLE
                    }

                    else -> {
                        bottomNavigationView.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent?.let {
            val destinationId = intent.getIntExtra(ARG_NAVIGATE_TO, DEFAULT_DESTINATION_ID)
            binding.bottomNavigationView.selectedItemId = destinationId
        }
    }

    companion object {
        private val DEFAULT_DESTINATION_ID = R.id.navigation_map
        const val ARG_NAVIGATE_TO = "navigate_to"
    }
}
