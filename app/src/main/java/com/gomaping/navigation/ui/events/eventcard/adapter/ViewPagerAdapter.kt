package com.gomaping.navigation.ui.events.eventcard.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gomaping.navigation.ui.events.eventcard.EventCardFragment
import com.gomaping.navigation.ui.events.eventcard.PhotoReviewEventFragment
import com.gomaping.navigation.ui.events.eventcard.ReviewerFragment
import com.gomaping.navigation.ui.events.eventcard.ReviewsEventFragment
import com.gomaping.navigation.ui.events.eventcard.ScheduleEventFragment

class ViewPagerAdapter(fragmentAdapter: EventCardFragment) : FragmentStateAdapter(fragmentAdapter) {

    private val fragments = listOf(
        ReviewerFragment.newInstance(),
        PhotoReviewEventFragment.newInstance(),
        ReviewsEventFragment.newInstance(),
        ScheduleEventFragment.newInstance(),
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}