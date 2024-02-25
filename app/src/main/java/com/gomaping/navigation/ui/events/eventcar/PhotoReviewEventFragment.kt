package com.gomaping.navigation.ui.events.eventcar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentPhotoReviewEventBinding
import com.gomaping.navigation.ui.events.eventcar.adapter.CardReviewAdapter

class PhotoReviewEventFragment : Fragment(R.layout.fragment_photo_review_event) {
    private val binding by viewBinding(FragmentPhotoReviewEventBinding::bind)
    private val adapter: CardReviewAdapter by lazy { CardReviewAdapter(getPhoto()) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = StaggeredGridLayoutManager( 2,StaggeredGridLayoutManager.VERTICAL )
        binding.rvPhoto.layoutManager = layoutManager
        binding.rvPhoto.adapter = adapter

    }

    private fun getPhoto(): List<ImageModel> {
        return listOf(
            ImageModel(R.drawable.tsirk_nikulina1),
            ImageModel(R.drawable.img_onboarding_1),
            ImageModel(R.drawable.tsirk_nikulina4),
            ImageModel(R.drawable.tsirk_nikulina3),
            ImageModel(R.drawable.tsirk_nikulina1),
            ImageModel(R.drawable.img_onboarding_3),
        )
    }
}