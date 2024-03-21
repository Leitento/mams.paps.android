package com.gomaping.navigation.ui.map.locationplaygroud

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentLocationScheduleBinding

class LocationScheduleFragment : Fragment(R.layout.fragment_location_schedule) {
    private val binding by viewBinding(FragmentLocationScheduleBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = LocationScheduleFragment()
    }
}