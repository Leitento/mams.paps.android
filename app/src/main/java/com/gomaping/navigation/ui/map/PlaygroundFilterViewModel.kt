package com.gomaping.navigation.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.gomaping.navigation.ui.events.SharedPrefUtils
import com.gomaping.navigation.ui.map.model.MapFilter
import com.gomaping.navigation.ui.map.model.MapFilterCheckBox
import com.gomaping.navigation.ui.map.model.MapFilterMain
import com.gomaping.navigation.ui.map.repository.FilterMapRepositoryImpl

class PlaygroundFilterViewModel (
    application: Application,
    private val sharedPrefUtils: SharedPrefUtils
) : AndroidViewModel(application) {
    private val appContext = application.applicationContext

    private val repository = FilterMapRepositoryImpl(appContext)

    fun getMainFilters(): List<MapFilterMain> {
        return repository.getMainFilter()
    }


    fun getFilters(filter: MapFilter): List<MapFilterCheckBox> {
        return repository.getFilters(filter)
    }

    fun loadSharePref(): Map<MapFilter, MutableList<MapFilterCheckBox>> {
        return sharedPrefUtils.loadMapFilters()
    }

    fun saveSharedPref(filterMap: MutableMap<MapFilter, MutableList<MapFilterCheckBox>>) {
        sharedPrefUtils.saveMapFilters(filterMap)
    }

    object Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            if (modelClass.isAssignableFrom(PlaygroundFilterViewModel::class.java)) {
                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val sharedPrefUtils = SharedPrefUtils(application)
                @Suppress("UNCHECKED_CAST")
                return PlaygroundFilterViewModel(application, sharedPrefUtils) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}