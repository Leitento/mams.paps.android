package com.gomaping.navigation.ui.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.databinding.ItemFilterCheckboxBinding
import com.gomaping.navigation.ui.map.model.MapFilterCheckBox

interface OnMapChooseListener {
    fun onClick(
        item: MapFilterCheckBox,
        position: Int,
    )
}

class MapFilterCheckBoxAdapter(
    private val listener: OnMapChooseListener,
) : ListAdapter<MapFilterCheckBox, MapFilterCheckBoxAdapter.MapFilterViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapFilterViewHolder {
        val binding = ItemFilterCheckboxBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MapFilterViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MapFilterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MapFilterViewHolder(
        val binding: ItemFilterCheckboxBinding,
        private val listener: OnMapChooseListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MapFilterCheckBox) {

            binding.checkboxEventFilter.isChecked = item.isChecked
            binding.checkboxEventFilter.text = item.itemName

            binding.checkboxEventFilter.setOnClickListener {
                val newItem = MapFilterCheckBox(
                    positionId = adapterPosition,
                    itemName = item.itemName,
                    isChecked = !item.isChecked,
                    item.filter
                )
                listener.onClick(newItem, adapterPosition)
            }

        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<MapFilterCheckBox>() {
        override fun areItemsTheSame(
            oldItem: MapFilterCheckBox,
            newItem: MapFilterCheckBox
        ): Boolean {
            return oldItem.positionId == newItem.positionId
        }

        override fun areContentsTheSame(
            oldItem: MapFilterCheckBox,
            newItem: MapFilterCheckBox
        ): Boolean {
            return oldItem == newItem
        }
    }
}