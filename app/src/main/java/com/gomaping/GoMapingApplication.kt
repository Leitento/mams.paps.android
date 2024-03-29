package com.gomaping

import android.app.Application
import com.gomaping.auth.data.local.AuthManager
import com.gomaping.navigation.ui.events.SharedPrefUtils
import com.yandex.mapkit.MapKitFactory

class GoMapingApplication : Application() {

    val authManager by lazy {
        AuthManager(this)
    }

    val sharedPrefUtils by lazy {
        SharedPrefUtils(this)
    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        SharedPrefUtils(this).ClearAll()
    }
}
