package com.gomaping.navigation.ui.events

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentEventsFilterBinding
import com.gomaping.navigation.ui.events.adapter.EventFilterCheckBoxAdapter
import com.gomaping.navigation.ui.events.adapter.EventFilterMainAdapter
import com.gomaping.navigation.ui.events.adapter.OnChooseListener
import com.gomaping.navigation.ui.events.adapter.OnItemClickListener
import com.gomaping.navigation.ui.events.model.EventFilterCheckBox
import com.gomaping.navigation.ui.events.model.Filter

class EventsFilterFragment : Fragment(R.layout.fragment_events_filter) {

    private val binding by viewBinding(FragmentEventsFilterBinding::bind)

    private val filterSettings =
        Filter.entries.associateBy({ it }, { mutableListOf<EventFilterCheckBox>() }).toMutableMap()

    private lateinit var adapter: EventFilterMainAdapter
    private lateinit var filterAdapter: EventFilterCheckBoxAdapter
    private val viewModel: EventFilterViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilterMap()

        adapter = EventFilterMainAdapter(object : OnItemClickListener {
            override fun onItemClick(filter: Filter) {
                val newData = filterSettings[filter] ?: listOf()
                filterAdapter.submitList(newData)
            }
        }, viewModel.getMainFilters())

        filterAdapter = EventFilterCheckBoxAdapter(object : OnChooseListener {
            override fun onClick(item: EventFilterCheckBox, position: Int) {
                val list = filterSettings[item.filter]?.toMutableList() ?: mutableListOf()
                val findPosition = list.find { it.positionId == position }
                findPosition?.let {
                    list[position] = item
                    filterSettings[item.filter] = list
                }
            }
        })
        val firstList = filterSettings[Filter.RATING]?.toList()
        if (firstList != null) {
             filterAdapter.submitList(firstList)
        } else {
            filterAdapter.submitList(viewModel.getFilters(Filter.RATING))
        }

        binding.recyclerFilter.adapter = filterAdapter
        binding.recyclerMainFilter.adapter = adapter

        val dividerItemDecoration =
            DividerItemDecoration(binding.recyclerFilter.context, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
            ?.let { dividerItemDecoration.setDrawable(it) }
        binding.recyclerFilter.addItemDecoration(dividerItemDecoration)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.buttonView.setOnClickListener {
            SharedPrefUtils.saveFilters(requireContext(), filterSettings.toMap())
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setFilterMap() {
        val filterMap = SharedPrefUtils.loadFilters(requireContext())
        if (filterMap.isEmpty()) {
            for (filter in Filter.entries) {
                val list = viewModel.getFilters(filter)
                filterSettings[filter] = list.toMutableList()
            }
        } else {
            for (filter in Filter.entries) {
                val list = filterMap[filter]
                list?.let {
                    filterSettings[filter] = list
                }
            }
        }
    }

}