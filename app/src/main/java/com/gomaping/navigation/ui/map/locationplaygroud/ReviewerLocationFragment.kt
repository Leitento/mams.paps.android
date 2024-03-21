package com.gomaping.navigation.ui.map.locationplaygroud

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentReviewerLocationBinding

class ReviewerLocationFragment : Fragment(R.layout.fragment_reviewer_location) {
    private val binding by viewBinding(FragmentReviewerLocationBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    companion object {
        fun newInstance() = ReviewerLocationFragment()
    }
}