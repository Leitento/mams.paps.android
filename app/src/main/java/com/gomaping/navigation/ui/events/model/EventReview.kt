package com.gomaping.navigation.ui.events.model

import com.gomaping.navigation.ui.events.eventcar.ImageModel

data class EventReview(
    val id: Int,
    val name: String,
    val rating: Int,
    val reviewDate: String,
    val reviewText: String,
    val reviewPhoto: List<ImageModel> = listOf(),
)