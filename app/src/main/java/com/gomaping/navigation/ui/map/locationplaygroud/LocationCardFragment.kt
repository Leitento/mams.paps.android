package com.gomaping.navigation.ui.map.locationplaygroud

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentLocationCardBinding
import com.gomaping.navigation.ui.events.eventcard.ImageModel
import com.gomaping.navigation.ui.events.eventcard.adapter.ViewPagerAdapter
import com.gomaping.navigation.ui.map.locationplaygroud.adapter.CardImageLocationAdapter
import com.gomaping.navigation.ui.map.locationplaygroud.adapter.ViewPagerLocationAdapter
import com.google.android.material.tabs.TabLayoutMediator

class LocationCardFragment : Fragment(R.layout.fragment_location_card) {
    private val binding by viewBinding(FragmentLocationCardBinding::bind)
    private val adapter: CardImageLocationAdapter by lazy { CardImageLocationAdapter(getPhoto()) }

    private val tabList = listOf(
        "Обзор",
        "Фото",
        "Отзывы",
        "Часы работы"
    )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpImages.adapter = adapter
        binding.viewPagerFragment.adapter = ViewPagerLocationAdapter(this)
        TabLayoutMediator(binding.tabLayoutFragment, binding.viewPagerFragment) { tab, pos ->
            tab.text = tabList[pos]
        }.attach()
        binding.toolbar.setOnClickListener {
            findNavController().popBackStack(R.id.nav_map_playground, true)
        }

        binding.vpImages.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Обновляем счетчик
                val totalPages = binding.vpImages.adapter?.itemCount ?:  0
                val couter = "${position +  1} / $totalPages"
                binding.pageCounter.text = couter
            }
        })
    }

    private fun getPhoto(): List<ImageModel> {
        return listOf(
            ImageModel(R.drawable.playground_one),
            ImageModel(R.drawable.playground_two),
            ImageModel(R.drawable.playground_three),
            ImageModel(R.drawable.playground_four),
            ImageModel(R.drawable.playground_five),
        )
    }
}