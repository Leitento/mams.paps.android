package com.gomaping.navigation.ui.map.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.R
import com.gomaping.databinding.ItemFilterMenuBinding
import com.gomaping.navigation.ui.map.model.MapFilter
import com.gomaping.navigation.ui.map.model.MapFilterMain
import com.yandex.runtime.Runtime

interface OnMapItemClickListener {
    fun onItemClick(filter: MapFilter)
}

class MapFilterMainAdapter(
    private val listener: OnMapItemClickListener,
    private val data: List<MapFilterMain>
) : RecyclerView.Adapter<MapFilterMainAdapter.MapFilterMainViewHolder>() {
    private var selectedPosition = 0
    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapFilterMainViewHolder {
        val binding = ItemFilterMenuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MapFilterMainViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MapFilterMainViewHolder, position: Int) {
        val item = data[position]
        val theme = Runtime.getApplicationContext().theme
        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(
                holder.itemView.context.resources.getColor(
                    R.color.red,
                    theme
                )
            )
            holder.binding.filterTitle.setTextColor(Color.WHITE)
            holder.binding.imageEventFilter.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
            holder.binding.filterTitle.setTextColor(Color.BLACK)
            holder.binding.imageEventFilter.setColorFilter(
                holder.itemView.context.resources.getColor(
                    R.color.red, theme
                ), PorterDuff.Mode.SRC_IN
            )
        }
        holder.bind(item)
    }

    inner class MapFilterMainViewHolder(
        val binding: ItemFilterMenuBinding,
        private val listener: OnMapItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MapFilterMain) {
            with(binding) {
                itemView.setOnClickListener {
                    listener.onItemClick(item.title)
                    val oldPosition = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(oldPosition)
                    notifyItemChanged(selectedPosition)
                }
                filterTitle.setText(item.titleResId)
                imageEventFilter.setImageResource(item.iconResId)
            }
        }
    }
}