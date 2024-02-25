package com.gomaping.navigation.ui.events.eventcar.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gomaping.navigation.ui.events.eventcar.EventCardFragment
import com.gomaping.navigation.ui.events.eventcar.PhotoReviewEventFragment
import com.gomaping.navigation.ui.events.eventcar.ReviewerFragment
import com.gomaping.navigation.ui.events.eventcar.ReviewsEventFragment
import com.gomaping.navigation.ui.events.eventcar.ScheduleEventFragment

class ViewPagerAdapter(fragmentAdapter: EventCardFragment) : FragmentStateAdapter(fragmentAdapter) {

    private val fragments = listOf(
        ReviewerFragment(),
        PhotoReviewEventFragment(),
        ReviewsEventFragment(),
        ScheduleEventFragment(),
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}