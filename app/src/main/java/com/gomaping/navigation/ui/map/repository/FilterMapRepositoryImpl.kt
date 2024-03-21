package com.gomaping.navigation.ui.map.repository

import android.content.Context
import com.gomaping.R
import com.gomaping.navigation.ui.map.model.MapFilter
import com.gomaping.navigation.ui.map.model.MapFilterCheckBox
import com.gomaping.navigation.ui.map.model.MapFilterMain

class FilterMapRepositoryImpl(private val context: Context) : FilterMapRepository {
    override fun getMainFilter(): List<MapFilterMain> {
        return listOf(
            MapFilterMain(
                MapFilter.RATING,
                R.drawable.ic_rating,
                R.string.event_rating,
            ),
            MapFilterMain(
                MapFilter.AGE_GROUP,
                R.drawable.ic_age_group,
                R.string.event_age_group,
            ),
            MapFilterMain(
                MapFilter.EQUIPMENT,
                R.drawable.ic_equipment,
                R.string.map_filter_equipment
            ),
            MapFilterMain(
                MapFilter.SIZE,
                R.drawable.ic_size,
                R.string.map_filter_size
            ),
            MapFilterMain(
                MapFilter.TOILET,
                R.drawable.ic_toilet,
                R.string.map_filter_toilet
            ),
            MapFilterMain(
                MapFilter.CANOPY,
                R.drawable.ic_canopy,
                R.string.map_filter_canopy
            ),
            MapFilterMain(
                MapFilter.BENCHES,
                R.drawable.ic_benches,
                R.string.map_filter_benches
            ),
            MapFilterMain(
                MapFilter.COVERAGE,
                R.drawable.ic_coverage,
                R.string.map_filter_coverage
            ),
            MapFilterMain(
                MapFilter.EQUIPMENT_MGN,
                R.drawable.ic_equipment_mgn,
                R.string.map_filter_equipment_mgn
            )
        )
    }

    override fun getFilters(filter: MapFilter): List<MapFilterCheckBox> {
        when (filter) {
            MapFilter.RATING -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.map_rating)
                return stringArray.map { MapFilterCheckBox(id++, it, false, filter) }
            }

            MapFilter.AGE_GROUP -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.map_age_group)
                return stringArray.map { MapFilterCheckBox(id++, it, false, filter) }
            }

            MapFilter.EQUIPMENT -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.map_equipment)
                return stringArray.map { MapFilterCheckBox(id++, it, false, filter) }
            }

            MapFilter.SIZE -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.map_size)
                return stringArray.map { MapFilterCheckBox(id++, it, false, filter) }
            }

            MapFilter.TOILET -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.map_toilet)
                return stringArray.map { MapFilterCheckBox(id++, it, false, filter) }
            }

            MapFilter.CANOPY -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.map_canopy)
                return stringArray.map { MapFilterCheckBox(id++, it, false, filter) }
            }

            MapFilter.BENCHES -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.map_benches)
                return stringArray.map { MapFilterCheckBox(id++, it, false, filter) }
            }

            MapFilter.COVERAGE -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.map_coverage)
                return stringArray.map { MapFilterCheckBox(id++, it, false, filter) }
            }

            MapFilter.EQUIPMENT_MGN -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.map_equipment_MGN)
                return stringArray.map { MapFilterCheckBox(id++, it, false, filter) }
            }
        }
    }
}