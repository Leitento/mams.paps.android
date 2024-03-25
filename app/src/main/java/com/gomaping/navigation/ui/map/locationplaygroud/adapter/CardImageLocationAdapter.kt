package com.gomaping.navigation.ui.map.locationplaygroud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.R
import com.gomaping.navigation.ui.events.eventcard.ImageModel

class CardImageLocationAdapter (private val photo: List<ImageModel>) :
    RecyclerView.Adapter<CardImageLocationAdapter.ImageHolder>() {
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