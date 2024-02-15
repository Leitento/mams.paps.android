package com.gomaping.navigation.ui.events.eventcar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentEventCardBinding
import com.gomaping.navigation.ui.events.eventcar.adapter.CardImageAdapter
import com.gomaping.navigation.ui.events.eventcar.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class EventCardFragment : Fragment(R.layout.fragment_event_card) {

    private val binding by viewBinding(FragmentEventCardBinding::bind)
    private val adapter: CardImageAdapter by lazy { CardImageAdapter(getPhoto()) }

    private val tabList = listOf(
        "Обзор",
        "Фото",
        "Отзывы"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpImages.adapter = adapter
        binding.viewPagerFragment.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayoutFragment, binding.viewPagerFragment) { tab, pos ->
            tab.text = tabList[pos]
        }.attach()

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    fun getPhoto(): List<ImageModel> {
        return listOf(
            ImageModel(R.drawable.tsirk_nikulina0),
            ImageModel(R.drawable.tsirk_nikulina1),
            ImageModel(R.drawable.tsirk_nikulina2),
            ImageModel(R.drawable.tsirk_nikulina3),
            ImageModel(R.drawable.tsirk_nikulina4),
        )
    }
}