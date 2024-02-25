package com.gomaping.navigation.ui.events.eventcar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentReviewsEventBinding
import com.gomaping.navigation.ui.events.eventcar.adapter.ReviewEventAdapter
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
                4,
                "2 Декабря 1970",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном все круто."
            ),
            EventReview(
                2,
                "Витя",
                5,
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
                5,
                "25 Февраля 1997",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном все круто."
            ),
            EventReview(
                5,
                "Толик",
                1,
                "25 Февраля 1999",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном все круто."
            ),
            EventReview(
                6,
                "Гарик",
                3,
                "25 Февраля 2001",
                "Отличное место для прогулки и отдыха с семьёй. Единственное не попали на фильмы в медиоцентре, младшей дочери 3 года и ей нельзя, а детских игровых где можно оставить ребёнка на час под присмотром нет. В остальном все круто."
            )
        )
    }

}