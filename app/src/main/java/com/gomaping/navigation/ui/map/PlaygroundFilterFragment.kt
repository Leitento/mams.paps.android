package com.gomaping.navigation.ui.map

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentPlaygroundFilterBinding
import com.gomaping.navigation.ui.map.adapter.MapFilterCheckBoxAdapter
import com.gomaping.navigation.ui.map.adapter.MapFilterMainAdapter
import com.gomaping.navigation.ui.map.adapter.OnMapChooseListener
import com.gomaping.navigation.ui.map.adapter.OnMapItemClickListener
import com.gomaping.navigation.ui.map.model.MapFilter
import com.gomaping.navigation.ui.map.model.MapFilterCheckBox

class PlaygroundFilterFragment : Fragment(R.layout.fragment_playground_filter) {
    private val binding by viewBinding(FragmentPlaygroundFilterBinding::bind)

    private val filterSettings =
        MapFilter.entries.associateBy({ it }, { mutableListOf<MapFilterCheckBox>() }).toMutableMap()

    private val viewModel: PlaygroundFilterViewModel by viewModels { PlaygroundFilterViewModel.Factory }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilterMap()
        initializeAdapters()

        binding.toolbar.setNavigationOnClickListener {
           findNavController().popBackStack(R.id.nav_map_filter, true)
        }

        binding.buttonView.setOnClickListener {
            viewModel.saveSharedPref(filterSettings)
            findNavController().popBackStack(R.id.nav_map_filter, true)
        }
    }
    private fun initializeAdapters() {

        val filterAdapter = MapFilterCheckBoxAdapter(object : OnMapChooseListener {
            override fun onClick(item: MapFilterCheckBox, position: Int) {
                val list = filterSettings[item.filter]?.toMutableList() ?: mutableListOf()
                val findPosition = list.find { it.positionId == position }
                findPosition?.let {
                    list[position] = item
                    filterSettings[item.filter] = list
                }
            }
        })

        val adapter = MapFilterMainAdapter(object : OnMapItemClickListener {
            override fun onItemClick(filter: MapFilter) {
                val newData = filterSettings[filter] ?: listOf()
                filterAdapter.submitList(newData) { filterAdapter.checkBoxStateArray.clear()}
            }
        }, viewModel.getMainFilters())
        binding.recyclerMainFilter.adapter = adapter
        val firstList = filterSettings[MapFilter.RATING]?.toList()
        if (firstList != null) {
            filterAdapter.submitList(firstList){ filterAdapter.checkBoxStateArray.clear()}
        } else {
            filterAdapter.submitList(viewModel.getFilters(MapFilter.RATING)){ filterAdapter.checkBoxStateArray.clear()}
        }
        binding.recyclerFilter.adapter = filterAdapter

        val dividerItemDecoration =
            DividerItemDecoration(binding.recyclerFilter.context, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
            ?.let { dividerItemDecoration.setDrawable(it) }
        binding.recyclerFilter.addItemDecoration(dividerItemDecoration)
    }

    private fun setFilterMap() {
        val filterMap = viewModel.loadSharePref()
        if (filterMap.isEmpty()) {
            for (filter in MapFilter.entries) {
                val list = viewModel.getFilters(filter)
                filterSettings[filter] = list.toMutableList()
            }
        } else {
            for (filter in MapFilter.entries) {
                val list = filterMap[filter]
                list?.let {
                    filterSettings[filter] = list
                }
            }
        }
    }
}