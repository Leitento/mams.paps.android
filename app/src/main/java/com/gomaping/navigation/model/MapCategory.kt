package com.gomaping.navigation.model

enum class MapCategory {
    NONE,
    PLAYGROUND,
    FOOD,
    DEVELOPMENT,
    EDUCATION,
    HEALTH,
    SHOP;

    companion object {
        fun fromName(name: String?): MapCategory {
            if (name == null) return NONE

            return try {
                MapCategory.valueOf(name)
            } catch (e: IllegalArgumentException) {
                NONE
            }
        }
    }
}
