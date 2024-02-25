package com.gomaping.navigation.ui.events

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentEventsBinding
import com.gomaping.navigation.ui.events.adapter.EventsAdapter
import com.gomaping.navigation.ui.events.adapter.FiltersAdapter
import com.gomaping.navigation.ui.events.adapter.OnClickListener
import com.gomaping.navigation.ui.events.adapter.OnEventClickListener
import com.gomaping.navigation.ui.events.eventcar.EventCardFragment
import com.gomaping.navigation.ui.events.model.EventModel
import com.gomaping.navigation.ui.events.model.Filter
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class EventsFragment : Fragment(R.layout.fragment_events) {

    private val binding by viewBinding(FragmentEventsBinding::bind)
    private val viewModel: EventsViewModel by viewModels { EventsViewModel.Factory }

    private val adapter: EventsAdapter by lazy {
        EventsAdapter(object : OnEventClickListener {
            override fun OnAction() {
                DialogFragment().show(childFragmentManager, "Action")
            }

            override fun OnEventCardView(event: EventModel) {
                navigateToCardEvent()
            }
        })
    }
    private val adapterFilter: FiltersAdapter by lazy {
        FiltersAdapter(object : OnClickListener {
            override fun OnDelete(position: Int) {
                val filter = viewModel.getFilterByPosition(position)
                filter?.let {
                    saveMap(it)
                    viewModel.removeFilterAt(position)
                }
                if (filter == null) {
                    viewModel.clearFilter()
                    viewModel.clearAll()
                }
                if (viewModel.getFilterSize() == 1) {
                    viewModel.clearFilter()
                }
                adapterFilter.submitList(viewModel.getFilter())
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerEvent.adapter = adapter
        binding.recyclerView.adapter = adapterFilter

        lifecycleScope.launch {
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
        val filterMap = viewModel.loadSharePref().toMutableMap()
        val listForKey = filterMap[filter] ?: mutableListOf()
        listForKey.forEach { it.isChecked = false }
        filterMap[filter] = listForKey
        viewModel.saveSharedPref(filterMap)
    }

    private fun navigateToFilters() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, EventsFilterFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun navigateToCardEvent() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, EventCardFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun observeFilter() {
        val filterMap = viewModel.loadSharePref().toMutableMap()
        viewModel.clearFilter()
        filterMap.forEach { (filter, checkBoxes) ->
            val hasSelected = checkBoxes.any { it.isChecked }
            if (hasSelected) {
                viewModel.addFilter(filter)
            }
        }

        if (viewModel.getFilter().isNotEmpty()) {
            viewModel.getFilter().sortedWith(compareBy { it?.ordinal })
            viewModel.addFilterToFirstPosition()
            adapterFilter.submitList(viewModel.getFilter())
        }

        if (viewModel.getFilterSize() == 0) {
            viewModel.clearFilter()
            adapterFilter.submitList(viewModel.getFilter())
        }
    }

    override fun onDestroy() {
        viewModel.clearAll()
        super.onDestroy()
    }

    companion object {
        private const val TAG = ""
        private const val LANGUAGE = "ru"
        private const val PATTERN = "dd.MM.yyyy"
    }
}
