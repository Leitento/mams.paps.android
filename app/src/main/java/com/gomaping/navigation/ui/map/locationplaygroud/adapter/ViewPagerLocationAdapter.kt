package com.gomaping.navigation.ui.map.locationplaygroud.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gomaping.navigation.ui.map.locationplaygroud.LocationCardFragment
import com.gomaping.navigation.ui.map.locationplaygroud.LocationReviewsFragment
import com.gomaping.navigation.ui.map.locationplaygroud.LocationScheduleFragment
import com.gomaping.navigation.ui.map.locationplaygroud.PhotoLocationFragment
import com.gomaping.navigation.ui.map.locationplaygroud.ReviewerLocationFragment

class ViewPagerLocationAdapter(fragmentAdapter: LocationCardFragment) :
    FragmentStateAdapter(fragmentAdapter) {

    private val fragments = listOf(
        ReviewerLocationFragment.newInstance(),
        PhotoLocationFragment.newInstance(),
        LocationReviewsFragment.newInstance(),
        LocationScheduleFragment.newInstance(),
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}