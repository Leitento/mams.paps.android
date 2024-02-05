package com.gomaping.navigation.ui.events

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentEventsBinding
import com.gomaping.navigation.ui.events.adapter.EventsAdapter
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class EventsFragment : Fragment(R.layout.fragment_events) {

    private val binding by viewBinding(FragmentEventsBinding::bind)
    private lateinit var adapter: EventsAdapter
    private val viewModel: EventsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = EventsAdapter()
        binding.recyclerEvent.adapter = adapter
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.events.collect {
                adapter.submitList(it)
            }
        }

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

    private fun navigateToFilters() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, EventsFilterFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object {
        private const val TAG = ""
        private const val LANGUAGE = "ru"
        private const val PATTERN = "dd.MM.yyyy"
    }
}
