package com.mams.paps.ui.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mams.paps.R
import com.yandex.mapkit.MapKitFactory

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController =
            findNavController(R.id.nav_host_fragment_container)

        bottomNavigationView.setupWithNavController(navController)

        MapKitFactory.initialize(this)
    }
}
