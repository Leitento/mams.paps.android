package com.gomaping.navigation.ui.events.repository

import com.gomaping.navigation.ui.events.model.EventModel
import kotlinx.coroutines.flow.Flow

interface EventRepository  {
   suspend fun getEvents(): Flow<List<EventModel>>
}