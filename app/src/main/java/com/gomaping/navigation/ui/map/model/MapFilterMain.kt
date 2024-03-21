package com.gomaping.navigation.ui.map.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MapFilterMain(
    val title: MapFilter,
    @DrawableRes val iconResId: Int,
    @StringRes val titleResId: Int,
)
