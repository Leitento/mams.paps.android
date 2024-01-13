package com.mams.paps.navigation.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
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

        fun createIntent(
            context: Context,
            @IdRes destinationId: Int = DEFAULT_DESTINATION_ID
        ): Intent {
            return Intent(context, NavigationActivity::class.java).apply {
                putExtra(ARG_NAVIGATE_TO, destinationId)
            }
        }
    }
}
