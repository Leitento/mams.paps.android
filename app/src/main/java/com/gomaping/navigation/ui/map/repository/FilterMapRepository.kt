package com.gomaping.navigation.ui.map.repository

import com.gomaping.navigation.ui.map.model.MapFilter
import com.gomaping.navigation.ui.map.model.MapFilterCheckBox
import com.gomaping.navigation.ui.map.model.MapFilterMain

interface FilterMapRepository {
    fun getMainFilter(): List<MapFilterMain>

    fun getFilters(filter: MapFilter): List<MapFilterCheckBox>
}