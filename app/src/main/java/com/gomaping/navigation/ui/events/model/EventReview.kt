package com.gomaping.navigation.ui.events.model

import com.gomaping.navigation.ui.events.eventcard.ImageModel
import com.gomaping.navigation.ui.events.eventcard.TypeReview

data class EventReview(
    val id: Int,
    val name: String,
    val rating: TypeReview,
    val reviewDate: String,
    val reviewText: String,
    val reviewPhoto: List<ImageModel> = listOf(),
)