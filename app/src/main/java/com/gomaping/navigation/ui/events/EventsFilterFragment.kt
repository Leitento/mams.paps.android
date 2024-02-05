package com.gomaping.navigation.ui.events

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentEventsFilterBinding
import com.gomaping.navigation.ui.events.adapter.EventFilterCheckBoxAdapter
import com.gomaping.navigation.ui.events.adapter.EventFilterMainAdapter
import com.gomaping.navigation.ui.events.model.EventFilterCheckBox
import com.gomaping.navigation.ui.events.model.EventFilterMain
import com.gomaping.navigation.ui.events.model.Filter

class EventsFilterFragment : Fragment(R.layout.fragment_events_filter) {

    private val binding by viewBinding(FragmentEventsFilterBinding::bind)

    private lateinit var adapter: EventFilterMainAdapter
    private lateinit var filterAdapter: EventFilterCheckBoxAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = EventFilterMainAdapter(getMainFilter())
        filterAdapter = EventFilterCheckBoxAdapter(getRatingFilter())
        binding.recyclerFilter.adapter = filterAdapter
        binding.recyclerMainFilter.adapter = adapter
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun getRatingFilter(): List<EventFilterCheckBox> {
        val stringArray = resources.getStringArray(R.array.event_rating)
        return stringArray.map { EventFilterCheckBox(it) }
    }

    private fun getMainFilter(): List<EventFilterMain> {
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
}