package com.gomaping.navigation.ui.map.model

data class MapFilterCheckBox(
    val positionId: Int,
    val itemName: String,
    var isChecked: Boolean = false,
    val filter: MapFilter,
)
