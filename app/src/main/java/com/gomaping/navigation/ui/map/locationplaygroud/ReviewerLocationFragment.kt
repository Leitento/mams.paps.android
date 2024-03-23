package com.gomaping.navigation.ui.map.locationplaygroud

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentReviewerLocationBinding
import com.gomaping.navigation.ui.map.MapViewModel
import com.gomaping.navigation.ui.map.locationplaygroud.adapter.ReviewerLocationAdapter
import com.gomaping.navigation.ui.map.model.MapFilter

class ReviewerLocationFragment : Fragment(R.layout.fragment_reviewer_location) {
    private val viewModel: MapViewModel by viewModels { MapViewModel.Factory }
    private val binding by viewBinding(FragmentReviewerLocationBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewTextDescription()
        getFilterCategories()
    }

    private fun viewTextDescription() {
        val fullText = getString(R.string.map_reviwer_location)
        var isExpanded = false
        binding.tvText.text = fullText
        with(binding) {
            tvText.setOnClickListener {
                if (isExpanded) {
                    tvText.maxLines = 3
                    tvText.ellipsize = TextUtils.TruncateAt.END
                    tvText.text = fullText
                    isExpanded = false
                } else {
                    tvText.maxLines = Integer.MAX_VALUE
                    tvText.ellipsize = null
                    tvText.text = fullText
                    isExpanded = true
                }
            }
        }
    }

    private fun getFilterCategories() {
        val mapFilter = viewModel.loadSharePref()
        val filteredMap = mapFilter.filter { (_, value) ->
            value.any { it.isChecked && it.filter != MapFilter.RATING }
        }.mapValues { (_, checkBoxes) ->
            checkBoxes.filter { it.isChecked }.toMutableList()
        }
        val allUnchecked = filteredMap.values.all { list ->
            list.all { mapFilterCheckBox -> !mapFilterCheckBox.isChecked }
        }
        if (allUnchecked) {
            val cardView = view?.findViewById<CardView>(R.id.card_filter)
            cardView?.setCardBackgroundColor(Color.TRANSPARENT)
        } else {
            val cardView = view?.findViewById<CardView>(R.id.card_filter)
            cardView?.setCardBackgroundColor(Color.WHITE)
            val adapter = ReviewerLocationAdapter(filteredMap)
            binding.rwFiltersItem.adapter = adapter
        }
    }

    companion object {
        fun newInstance() = ReviewerLocationFragment()
    }
}