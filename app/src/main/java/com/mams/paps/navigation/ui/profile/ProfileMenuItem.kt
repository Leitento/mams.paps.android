package com.mams.paps.navigation.ui.profile

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mams.paps.R

data class ProfileMenuItem(
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int,
    @ColorRes val backgroundColorResId: Int = R.color.red
)
