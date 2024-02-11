package com.gomaping.navigation.ui.events.repository

import android.content.Context
import com.gomaping.GoMapingApplication
import com.gomaping.R
import com.gomaping.navigation.ui.events.model.EventFilterCheckBox
import com.gomaping.navigation.ui.events.model.EventFilterMain
import com.gomaping.navigation.ui.events.model.Filter

class FilterEventRepositoryImpl: FilterEventRepository {
    private val context: Context = GoMapingApplication.applicationContext()


    override fun getMainFilter(): List<EventFilterMain> {
        return listOf(
            EventFilterMain(
                Filter.RATING,
                R.drawable.ic_rating,
                R.string.event_rating,
            ),
            EventFilterMain(
                Filter.AGE_GROUP,
                R.drawable.ic_age_group,
                R.string.event_age_group,
            ),
            EventFilterMain(
                Filter.EXHIBITIONS,
                R.drawable.ic_exhibitions,
                R.string.event_exhibitions,
            ),
            EventFilterMain(
                Filter.CONCERTS,
                R.drawable.ic_concerts,
                R.string.event_concerts,
            ),
            EventFilterMain(
                Filter.THEATRE,
                R.drawable.ic_theatre,
                R.string.event_theatre,
            ),
            EventFilterMain(
                Filter.FESTIVALS,
                R.drawable.ic_festivals,
                R.string.event_festivals,
            ),
            EventFilterMain(
                Filter.SPORT,
                R.drawable.ic_sport,
                R.string.event_sport,
            ),
        )
    }

    override fun getFilters(filter: Filter): List<EventFilterCheckBox> {
        when (filter) {
            Filter.RATING -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.event_rating)
                return stringArray.map { EventFilterCheckBox(id++, it, false, filter) }
            }

            Filter.AGE_GROUP -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.event_age_group)
                return stringArray.map { EventFilterCheckBox(id++, it, false, filter) }
            }

            Filter.EXHIBITIONS -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.event_exhibitions)
                return stringArray.map { EventFilterCheckBox(id++, it, false, filter) }
            }

            Filter.CONCERTS -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.event_concerts)
                return stringArray.map { EventFilterCheckBox(id++, it, false, filter) }
            }

            Filter.THEATRE -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.event_theatre)
                return stringArray.map { EventFilterCheckBox(id++, it, false, filter) }
            }

            Filter.FESTIVALS -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.event_festivals)
                return stringArray.map { EventFilterCheckBox(id++, it, false, filter) }
            }

            Filter.SPORT -> {
                var id = 0
                val stringArray = context.resources.getStringArray(R.array.event_sport)
                return stringArray.map { EventFilterCheckBox(id++, it, false, filter) }
            }
        }
    }
}