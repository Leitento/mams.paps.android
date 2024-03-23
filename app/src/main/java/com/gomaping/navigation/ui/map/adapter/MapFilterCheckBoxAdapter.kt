package com.gomaping.navigation.ui.map.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.isNotEmpty
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.databinding.ItemFilterCheckboxBinding
import com.gomaping.navigation.ui.map.model.MapFilter
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
    val checkBoxStateArray = SparseBooleanArray()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapFilterViewHolder {
        val binding = ItemFilterCheckboxBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MapFilterViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MapFilterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        when (item.filter) {
            MapFilter.TOILET, MapFilter.CANOPY, MapFilter.BENCHES, MapFilter.EQUIPMENT_MGN -> {
                setCheckBoxEnable(holder,position,item)
            }
            else -> {
                holder.binding.checkboxEventFilter.isChecked = item.isChecked
            }
        }
    }

    inner class MapFilterViewHolder(
        val binding: ItemFilterCheckboxBinding,
        private val listener: OnMapChooseListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(item: MapFilterCheckBox) {
            binding.checkboxEventFilter.text = item.itemName
            binding.checkboxEventFilter.setOnClickListener {
                when (item.filter) {
                    MapFilter.TOILET, MapFilter.CANOPY, MapFilter.BENCHES, MapFilter.EQUIPMENT_MGN -> {
                        // Сброс состояния всех других CheckBox
                        for (i in 0 until checkBoxStateArray.size()) {
                            if (checkBoxStateArray.keyAt(i) != adapterPosition) {
                                checkBoxStateArray.put(checkBoxStateArray.keyAt(i), false)
                            }
                        }
                        checkBoxStateArray.put(adapterPosition, true)
                        notifyDataSetChanged()
                    }

                    else -> {
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
        }
    }

    private fun setCheckBoxEnable(holder: MapFilterViewHolder, position: Int, item: MapFilterCheckBox) {
        if (checkBoxStateArray.isNotEmpty()) {
            val isChecked = checkBoxStateArray.get(position, false)
            holder.binding.checkboxEventFilter.isChecked = isChecked
            val newItem = MapFilterCheckBox(
                positionId = position,
                itemName = item.itemName,
                isChecked = isChecked,
                item.filter
            )
            listener.onClick(newItem, position)
        } else {
            holder.binding.checkboxEventFilter.isChecked = item.isChecked
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