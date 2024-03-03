package com.gomaping.navigation.ui.events.eventcard.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.R
import com.gomaping.databinding.ItemEventReviewBinding
import com.gomaping.navigation.ui.events.eventcard.TypeReview
import com.gomaping.navigation.ui.events.model.EventReview
import com.yandex.runtime.Runtime

class ReviewEventAdapter :
    androidx.recyclerview.widget.ListAdapter<EventReview, ReviewEventAdapter.EventReviewViewHolder>(
        DiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventReviewViewHolder {
        val binding = ItemEventReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventReviewViewHolder, position: Int) {
        val eventReview = getItem(position)
        holder.bind(eventReview)
    }

    class EventReviewViewHolder(
        val binding: ItemEventReviewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(eventReview: EventReview) {

            with(binding) {
                reviewName.text = eventReview.name
                reviewEventDate.text = eventReview.reviewDate
                reviewEventText.text = eventReview.reviewText
                if (eventReview.reviewPhoto.isNotEmpty()) {
                    val adapter = CardImageReviewAdapter(eventReview.reviewPhoto)
                    binding.reviewPhoto.adapter = adapter
                }
                if (eventReview.reviewPhoto.isEmpty()) {
                    reviewPhoto.visibility = View.GONE
                }
                when (eventReview.rating) {
                    TypeReview.DISGUSTING -> {
                        star1.setColorStar(itemView)
                    }

                    TypeReview.BADLY -> {
                        star1.setColorStar(itemView)
                        star2.setColorStar(itemView)
                    }

                    TypeReview.NOT_BAD -> {
                        star1.setColorStar(itemView)
                        star2.setColorStar(itemView)
                        star3.setColorStar(itemView)
                    }

                    TypeReview.GOOD -> {
                        star1.setColorStar(itemView)
                        star2.setColorStar(itemView)
                        star3.setColorStar(itemView)
                        star4.setColorStar(itemView)
                    }

                    TypeReview.EXCELLENT -> {
                        star1.setColorStar(itemView)
                        star2.setColorStar(itemView)
                        star3.setColorStar(itemView)
                        star4.setColorStar(itemView)
                        star5.setColorStar(itemView)
                    }
                }
            }
        }

        private fun ImageView.setColorStar(itemView: View) {
            val theme = Runtime.getApplicationContext().theme
            setColorFilter(
                itemView.context.resources.getColor(
                    R.color.red, theme
                ), PorterDuff.Mode.SRC_IN
            )
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<EventReview>() {
        override fun areItemsTheSame(oldItem: EventReview, newItem: EventReview): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventReview, newItem: EventReview): Boolean {
            return oldItem == newItem
        }
    }
}