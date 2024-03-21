package com.gomaping.navigation.ui.map.locationplaygroud

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentPhotoLocationBinding
import com.gomaping.navigation.ui.events.eventcard.ImageModel
import com.gomaping.navigation.ui.map.locationplaygroud.adapter.CardReviewLocationAdapter

class PhotoLocationFragment : Fragment(R.layout.fragment_photo_location) {
    private val binding by viewBinding(FragmentPhotoLocationBinding::bind)
    private val adapter: CardReviewLocationAdapter by lazy { CardReviewLocationAdapter(getPhoto()) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 0)  2 else 1
            }
        }
        binding.rvPhoto.layoutManager = layoutManager
        binding.rvPhoto.adapter = adapter
    }

    companion object {
        fun newInstance() = PhotoLocationFragment()
    }
    private fun getPhoto(): List<ImageModel> {
        return listOf(
            ImageModel(R.drawable.playground_one),
            ImageModel(R.drawable.playground_two),
            ImageModel(R.drawable.playground_three),
            ImageModel(R.drawable.playground_one),
            ImageModel(R.drawable.playground_two),
            ImageModel(R.drawable.playground_three),
        )
    }
}