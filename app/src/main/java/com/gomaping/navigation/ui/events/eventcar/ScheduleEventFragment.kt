package com.gomaping.navigation.ui.events.eventcar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentScheduleEventBinding

class ScheduleEventFragment : Fragment(R.layout.fragment_schedule_event) {

    private val binding by viewBinding(FragmentScheduleEventBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}