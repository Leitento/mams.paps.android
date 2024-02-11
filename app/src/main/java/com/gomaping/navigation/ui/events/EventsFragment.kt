package com.gomaping.navigation.ui.events

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentEventsBinding
import com.gomaping.navigation.ui.events.adapter.EventsAdapter
import com.gomaping.navigation.ui.events.adapter.FiltersAdapter
import com.gomaping.navigation.ui.events.adapter.OnClickListener
import com.gomaping.navigation.ui.events.model.Filter
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class EventsFragment : Fragment(R.layout.fragment_events) {

    private val binding by viewBinding(FragmentEventsBinding::bind)
    private val viewModel: EventsViewModel by viewModels()
    private val checkedItems = mutableListOf<Filter?>()

    private val adapter: EventsAdapter by lazy {
        EventsAdapter {

        }
    }
    private val adapterFilter: FiltersAdapter by lazy {
        FiltersAdapter(object : OnClickListener {
            override fun OnDelete(position: Int) {
                val filter = checkedItems[position]
                filter?.let {
                    saveMap(it)
                    checkedItems.removeAt(position)
                }
                if (filter == null) {
                    checkedItems.clear()
                    SharedPrefUtils.ClearAll(requireContext())
                }
                if (checkedItems.size == 1) {
                    checkedItems.clear()
                }
                adapterFilter.submitList(checkedItems.toList())
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerEvent.adapter = adapter
        binding.recyclerView.adapter = adapterFilter

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.events.collect {
                adapter.submitList(it)
            }
        }
        observeFilter()

        binding.chooseDate.setOnClickListener {
            createDatePicker()
        }

        binding.filter.setOnClickListener {
            navigateToFilters()
        }
    }

    private fun createDatePicker() {
        val initialDate = System.currentTimeMillis()
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(initialDate)
            .setTheme(R.style.ThemeOverlay_App_DatePicker)
            .build()
        datePicker.addOnPositiveButtonClickListener { long ->
            val dateChosen = SimpleDateFormat(
                PATTERN,
                Locale(LANGUAGE)
            ).format(long)
            binding.chooseDate.text = dateChosen
        }
        datePicker.show(childFragmentManager, TAG)
    }

    private fun saveMap(filter: Filter) {
        val filterMap = SharedPrefUtils.loadFilters(requireContext()).toMutableMap()
        val listForKey = filterMap[filter] ?: mutableListOf()
        listForKey.forEach { it.isChecked = false }
        filterMap[filter] = listForKey
        SharedPrefUtils.saveFilters(requireContext(), filterMap)
    }

    private fun navigateToFilters() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, EventsFilterFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun observeFilter() {
        val filterMap = SharedPrefUtils.loadFilters(requireContext())
        checkedItems.clear()
        filterMap.forEach { (filter, checkBoxes) ->
            val hasSelected = checkBoxes.any { it.isChecked }
            if (hasSelected) {
                checkedItems.add(filter)
            }
        }

        if (checkedItems.isNotEmpty()) {
            checkedItems.sortedWith(compareBy { it?.ordinal })
            checkedItems.add(0, null)
            adapterFilter.submitList(checkedItems.toList())
        }

        if (checkedItems.size == 0) {
            checkedItems.clear()
            adapterFilter.submitList(checkedItems.toList())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SharedPrefUtils.ClearAll(requireContext())
    }

    companion object {
        private const val TAG = ""
        private const val LANGUAGE = "ru"
        private const val PATTERN = "dd.MM.yyyy"
    }
}
