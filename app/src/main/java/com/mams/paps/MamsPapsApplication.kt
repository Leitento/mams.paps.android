package com.mams.paps

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class MamsPapsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
    }
}
