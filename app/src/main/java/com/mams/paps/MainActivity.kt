package com.mams.paps

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mams.paps.databinding.ActivityMainBinding
import com.mams.paps.ui.OnboardingActivity
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        MapKitFactory.initialize(this)

        val intent = Intent(this, OnboardingActivity::class.java)
        startActivity(intent)
    }
}
