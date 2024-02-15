package com.gomaping.navigation.ui.events.eventcar.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gomaping.navigation.ui.events.EventsFilterFragment
import com.gomaping.navigation.ui.events.EventsFragment
import com.gomaping.navigation.ui.events.eventcar.EventCardFragment
import com.gomaping.navigation.ui.events.eventcar.ReviewerFragment

class ViewPagerAdapter(fragmentAdapter: EventCardFragment) : FragmentStateAdapter(fragmentAdapter) {

    private val fragments = listOf(
        ReviewerFragment(),
        EventsFilterFragment(),
        EventsFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}