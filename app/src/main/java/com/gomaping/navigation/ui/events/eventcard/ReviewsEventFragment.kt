package com.gomaping.navigation.ui.events.eventcard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentReviewsEventBinding
import com.gomaping.navigation.ui.events.eventcard.adapter.ReviewEventAdapter
import com.gomaping.navigation.ui.events.model.EventReview

class ReviewsEventFragment : Fragment(R.layout.fragment_reviews_event) {
    private val binding by viewBinding(FragmentReviewsEventBinding::bind)
    private val adapter: ReviewEventAdapter by lazy { ReviewEventAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvReviewEvent.adapter = adapter
        val review = getEventReview()
        adapter.submitList(review)
    }

    private fun getEventReview(): List<EventReview> {
        return listOf(
            EventReview(
                1,
                "Коля",
                TypeReview.DISGUSTING,
                "2 Декабря 1970",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном все круто."
            ),
            EventReview(
                2,
                "Витя",
                TypeReview.EXCELLENT,
                "15 Января 1980",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном всекруто.",
                listOf(
                    ImageModel(R.drawable.tsirk_nikulina0),
                    ImageModel(R.drawable.tsirk_nikulina1),
                    ImageModel(R.drawable.tsirk_nikulina2),
                )
            ),
            EventReview(
                4,
                "Мария",
                TypeReview.NOT_BAD,
                "25 Февраля 1997",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном все круто."
            ),
            EventReview(
                5,
                "Толик",
                TypeReview.BADLY,
                "25 Февраля 1999",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном все круто."
            ),
            EventReview(
                6,
                "Гарик",
                TypeReview.GOOD,
                "25 Февраля 2001",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном все круто."
            )
        )
    }
    companion object {
        fun newInstance() = ReviewsEventFragment()
    }
}