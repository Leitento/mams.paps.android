package com.gomaping.navigation.ui.map.locationplaygroud.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.R
import com.gomaping.databinding.ItemReviewsBinding
import com.gomaping.navigation.ui.events.eventcard.TypeReview
import com.gomaping.navigation.ui.events.eventcard.adapter.CardImageReviewAdapter
import com.gomaping.navigation.ui.map.model.LocationReviews
import com.yandex.runtime.Runtime

class ReviewsLocationAdapter :
    androidx.recyclerview.widget.ListAdapter<LocationReviews, ReviewsLocationAdapter.LocationReviewsViewHolder>(
        DiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationReviewsViewHolder {
        val binding = ItemReviewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LocationReviewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationReviewsViewHolder, position: Int) {
        val locationReview = getItem(position)
        holder.bind(locationReview)
    }

    class LocationReviewsViewHolder(
        val binding: ItemReviewsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(locationReview: LocationReviews) {

            with(binding) {
                reviewName.text = locationReview.name
                reviewEventDate.text = locationReview.reviewDate
                reviewEventText.text = locationReview.reviewText
                if (locationReview.reviewPhoto.isNotEmpty()) {
                    val adapter = CardImageReviewAdapter(locationReview.reviewPhoto)
                    binding.reviewPhoto.adapter = adapter
                }
                if (locationReview.reviewPhoto.isEmpty()) {
                    reviewPhoto.visibility = View.GONE
                }
                when (locationReview.rating) {
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

    private class DiffCallback : DiffUtil.ItemCallback<LocationReviews>() {
        override fun areItemsTheSame(oldItem: LocationReviews, newItem: LocationReviews): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LocationReviews, newItem: LocationReviews): Boolean {
            return oldItem == newItem
        }
    }
}