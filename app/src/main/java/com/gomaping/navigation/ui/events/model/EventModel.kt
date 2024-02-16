package com.gomaping.navigation.ui.events.model

data class EventModel (
    val id: Long = 0L,
    val title: String = "",
    val link: String = "",
    val descriptions: String = "",
    val schedule: String = "",
    val breakTime: String = "",
    val reviews: String = "",
    val timeTo: String = "",
    val distance: String = "",
)