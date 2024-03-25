package com.gomaping.navigation.ui.map.locationplaygroud

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentLocationReviewsBinding
import com.gomaping.navigation.ui.events.eventcard.ImageModel
import com.gomaping.navigation.ui.events.eventcard.TypeReview
import com.gomaping.navigation.ui.map.locationplaygroud.adapter.ReviewsLocationAdapter
import com.gomaping.navigation.ui.map.model.LocationReviews

class LocationReviewsFragment : Fragment(R.layout.fragment_location_reviews) {
    private val binding by viewBinding(FragmentLocationReviewsBinding::bind)
    private val adapter: ReviewsLocationAdapter by lazy { ReviewsLocationAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvReviewLocation.adapter = adapter
        val review = getLocationReviews()
        adapter.submitList(review)
    }
    private fun getLocationReviews(): List<LocationReviews> {
        return listOf(
            LocationReviews(
                1,
                "Коля",
                TypeReview.DISGUSTING,
                "2 Декабря 1970",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном все круто."
            ),
            LocationReviews(
                2,
                "Витя",
                TypeReview.EXCELLENT,
                "15 Января 1980",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном всекруто.",
                listOf(
                    ImageModel(R.drawable.playground_one),
                    ImageModel(R.drawable.playground_two),
                    ImageModel(R.drawable.playground_three),
                    ImageModel(R.drawable.playground_four),
                    ImageModel(R.drawable.playground_five),
                )
            ),
            LocationReviews(
                4,
                "Мария",
                TypeReview.NOT_BAD,
                "25 Февраля 1997",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном все круто."
            ),
            LocationReviews(
                5,
                "Толик",
                TypeReview.BADLY,
                "25 Февраля 1999",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном все круто."
            ),
            LocationReviews(
                6,
                "Гарик",
                TypeReview.GOOD,
                "25 Февраля 2001",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном все круто."
            )
        )
    }
    companion object {
        fun newInstance() = LocationReviewsFragment()
    }
}