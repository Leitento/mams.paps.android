package com.gomaping.navigation.ui.events.repository

import com.gomaping.navigation.ui.events.model.EventFilterCheckBox
import com.gomaping.navigation.ui.events.model.EventFilterMain
import com.gomaping.navigation.ui.events.model.Filter

interface FilterEventRepository {

    fun getMainFilter(): List<EventFilterMain>

    fun getFilters(filter: Filter): List<EventFilterCheckBox>

}