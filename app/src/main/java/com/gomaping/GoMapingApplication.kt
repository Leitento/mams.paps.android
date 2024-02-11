package com.gomaping

import android.app.Application
import android.content.Context
import com.gomaping.auth.data.local.AuthManager
import com.yandex.mapkit.MapKitFactory

class GoMapingApplication : Application() {

    init {
        instance = this
    }

    val authManager by lazy {
        AuthManager(this)
    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
    }

    companion object {
        private var instance: GoMapingApplication? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

}
