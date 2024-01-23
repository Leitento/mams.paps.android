package com.gomaping.navigation.ui.profile

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gomaping.R

class ProfileMenuItem(
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int,
    @ColorRes val backgroundColorResId: Int = R.color.red
)
