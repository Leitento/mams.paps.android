package com.mams.paps

import android.app.Application
import com.mams.paps.auth.data.local.AuthManager
import com.yandex.mapkit.MapKitFactory

class MamsPapsApplication : Application() {

    val authManager by lazy {
        AuthManager(this)
    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
    }
}
