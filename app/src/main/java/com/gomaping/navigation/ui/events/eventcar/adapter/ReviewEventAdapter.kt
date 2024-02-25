package com.gomaping.navigation.ui.events.eventcar.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.R
import com.gomaping.databinding.ItemEventReviewBinding
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
        if (eventReview.reviewPhoto.isNotEmpty()) {
            val adapter = CardImageReviewAdapter(eventReview.reviewPhoto)
            //  val layoutManager = GridLayoutManager(holder.itemView.context,1, GridLayoutManager.HORIZONTAL, false)
            val layoutManager =
                LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
            holder.binding.reviewPhoto.layoutManager = layoutManager
            holder.binding.reviewPhoto.adapter = adapter
        }
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
                if (eventReview.reviewPhoto.isEmpty()) {
                    reviewPhoto.visibility = View.GONE
                }
                when (eventReview.rating) {
                    1 -> {
                        star1.setColorStar(itemView)
                    }

                    2 -> {
                        star1.setColorStar(itemView)
                        star2.setColorStar(itemView)
                    }

                    3 -> {

                        star1.setColorStar(itemView)
                        star2.setColorStar(itemView)
                        star3.setColorStar(itemView)
                    }

                    4 -> {
                        star1.setColorStar(itemView)
                        star2.setColorStar(itemView)
                        star3.setColorStar(itemView)
                        star4.setColorStar(itemView)
                    }

                    5 -> {
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