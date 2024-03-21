package com.gomaping.navigation.ui.events

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.gomaping.navigation.ui.events.model.EventFilterCheckBox
import com.gomaping.navigation.ui.events.model.EventFilterMain
import com.gomaping.navigation.ui.events.model.Filter
import com.gomaping.navigation.ui.events.repository.FilterEventRepositoryImpl

class EventFilterViewModel(
    application: Application,
    private val sharedPrefUtils: SharedPrefUtils
) : AndroidViewModel(application) {
    private val appContext = application.applicationContext

    private val repository = FilterEventRepositoryImpl(appContext)

    fun getMainFilters(): List<EventFilterMain> {
        return repository.getMainFilter()
    }


    fun getFilters(filter: Filter): List<EventFilterCheckBox> {
        return repository.getFilters(filter)
    }

    fun loadSharePref(): Map<Filter, MutableList<EventFilterCheckBox>> {
        return sharedPrefUtils.loadEventFilters()
    }

    fun saveSharedPref(filterMap: MutableMap<Filter, MutableList<EventFilterCheckBox>>) {
        sharedPrefUtils.saveEventFilters(filterMap)
    }

    object Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            if (modelClass.isAssignableFrom(EventFilterViewModel::class.java)) {
                val application = extras[APPLICATION_KEY] as Application
                val sharedPrefUtils = SharedPrefUtils(application)
                @Suppress("UNCHECKED_CAST")
                return EventFilterViewModel(application, sharedPrefUtils) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}