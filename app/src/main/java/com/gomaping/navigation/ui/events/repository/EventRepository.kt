package com.gomaping.navigation.ui.events.repository

import com.gomaping.navigation.ui.events.model.EventModel

interface EventRepository  {
    fun getEvents(): List<EventModel>
}