package com.gomaping.navigation.ui.events.eventcar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.R
import com.gomaping.navigation.ui.events.eventcar.ImageModel

class CardImageReviewAdapter (private val photo: List<ImageModel>) :
    RecyclerView.Adapter<CardImageReviewAdapter.ImageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_image_view_pager, parent, false)
        return ImageHolder(view)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.imageView).let {
            it.setImageResource(photo[position].iconResId)
        }
    }

    override fun getItemCount(): Int {
        return photo.size
    }

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}