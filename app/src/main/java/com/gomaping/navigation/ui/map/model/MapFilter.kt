package com.gomaping.navigation.ui.map.model

import androidx.annotation.DrawableRes
import com.gomaping.R

enum class MapFilter(@DrawableRes val image: Int) {
    RATING(R.drawable.ic_rating),
    AGE_GROUP(R.drawable.ic_image_age_group),
    EQUIPMENT(R.drawable.ic_image_equipment),
    SIZE(R.drawable.ic_image_size),
    TOILET(R.drawable.ic_image_toilet),
    CANOPY(R.drawable.ic_image_canopy),
    BENCHES(R.drawable.ic_image_banches),
    COVERAGE(R.drawable.ic_image_coverage),
    EQUIPMENT_MGN(R.drawable.ic_image_equipment_mgh),
}