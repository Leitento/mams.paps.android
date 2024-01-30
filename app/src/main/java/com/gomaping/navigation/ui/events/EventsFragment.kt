package com.gomaping.navigation.ui.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentEventsBinding
import com.gomaping.databinding.FragmentLoginBinding

class EventsFragment : Fragment(R.layout.fragment_events) {

    private val binding by viewBinding(FragmentEventsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}