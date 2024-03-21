package com.gomaping.navigation.ui.map.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.R
import com.gomaping.navigation.ui.map.model.MapFilter

class LocationBottomSheetFilterAdapter(private val filter: List<MapFilter>) :
    RecyclerView.Adapter<LocationBottomSheetFilterAdapter.ImageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image_categories, parent, false)
        return ImageHolder(view)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val image = holder.itemView.findViewById<ImageView>(R.id.filter_categories)
        when (filter[position]) {
            MapFilter.RATING -> { image.setImageResource(filter[position].image) }
            MapFilter.AGE_GROUP -> { image.setImageResource(filter[position].image) }
            MapFilter.EQUIPMENT -> { image.setImageResource(filter[position].image) }
            MapFilter.BENCHES -> { image.setImageResource(filter[position].image) }
            MapFilter.CANOPY -> { image.setImageResource(filter[position].image) }
            MapFilter.TOILET -> { image.setImageResource(filter[position].image) }
            MapFilter.SIZE -> { image.setImageResource(filter[position].image) }
            MapFilter.COVERAGE -> { image.setImageResource(filter[position].image) }
            MapFilter.EQUIPMENT_MGN -> { image.setImageResource(filter[position].image) }
        }
    }

    override fun getItemCount(): Int {
        return filter.size
    }

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}