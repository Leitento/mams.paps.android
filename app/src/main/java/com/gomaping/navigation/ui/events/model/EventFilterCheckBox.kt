package com.gomaping.navigation.ui.events.model

data class EventFilterCheckBox(
    val positionId: Int,
    val itemName: String,
    var isChecked: Boolean = false,
    val filter: Filter,
)