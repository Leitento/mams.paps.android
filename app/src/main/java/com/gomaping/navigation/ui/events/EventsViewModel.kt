package com.gomaping.navigation.ui.events

import androidx.lifecycle.ViewModel
import com.gomaping.navigation.ui.events.model.EventModel
import com.gomaping.navigation.ui.events.repository.EventRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventsViewModel : ViewModel() {
    private val repositoryImpl = EventRepositoryImpl()
    private val _events = MutableStateFlow<List<EventModel>>(listOf())
    val events = _events.asStateFlow()

    init {
        val data = repositoryImpl.getEvents()
        _events.value = data
    }
}