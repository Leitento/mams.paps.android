package com.gomaping.navigation.ui.map.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.R
import com.gomaping.navigation.ui.events.eventcard.ImageModel

interface OnClickLocationListener {
    fun onClickLocation()
}

class LocationBottomSheetPhotoAdapter(
    val photo: List<ImageModel>,
    val listener: OnClickLocationListener
) :
    RecyclerView.Adapter<LocationBottomSheetPhotoAdapter.ImageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_location_photo, parent, false)
        return ImageHolder(view)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val item = holder.itemView.findViewById<ImageView>(R.id.image_location_photo)
        item.setImageResource(photo[position].iconResId)
        holder.itemView.setOnClickListener {
           listener.onClickLocation()
        }
    }

    override fun getItemCount(): Int {
        return photo.size
    }

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}