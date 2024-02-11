package com.gomaping.navigation.ui.events

import androidx.lifecycle.ViewModel
import com.gomaping.navigation.ui.events.model.EventFilterCheckBox
import com.gomaping.navigation.ui.events.model.EventFilterMain
import com.gomaping.navigation.ui.events.model.Filter
import com.gomaping.navigation.ui.events.repository.FilterEventRepositoryImpl

class EventFilterViewModel : ViewModel() {

    private val repository = FilterEventRepositoryImpl()

    fun getMainFilters(): List<EventFilterMain> {
        return repository.getMainFilter()
    }

    fun getFilters(filter: Filter): List<EventFilterCheckBox> {
        return repository.getFilters(filter)
    }

}