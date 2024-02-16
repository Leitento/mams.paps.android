package com.gomaping.navigation.ui.events.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class EventFilterMain(
    val title: Filter,
    @DrawableRes val iconResId: Int,
    @StringRes val titleResId: Int,
)
