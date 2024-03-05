package com.gomaping.navigation.ui.events.eventcard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentReviewerBinding

class ReviewerFragment : Fragment(R.layout.fragment_reviewer) {
    private val binding by viewBinding(FragmentReviewerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    companion object {
        fun newInstance() = ReviewerFragment()
    }
}