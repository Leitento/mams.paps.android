package com.mams.paps.ui.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mams.paps.R
import com.mams.paps.databinding.ActivityMapBinding
import com.yandex.mapkit.MapKitFactory

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController =
            findNavController(R.id.nav_host_fragment_container)

        bottomNavigationView.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_add,
                R.id.navigation_geo,
                R.id.navigation_map,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}
