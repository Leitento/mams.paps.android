package com.gomaping.navigation.ui.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.databinding.ItemFilterCheckboxBinding
import com.gomaping.navigation.ui.events.model.EventFilterCheckBox

interface OnChooseListener {
    fun onClick(
        item: EventFilterCheckBox,
        position: Int,
    )
}

class EventFilterCheckBoxAdapter(
    private val listener: OnChooseListener,
) : ListAdapter<EventFilterCheckBox, EventFilterCheckBoxAdapter.EventFilterViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventFilterViewHolder {
        val binding = ItemFilterCheckboxBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventFilterViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: EventFilterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class EventFilterViewHolder(
        val binding: ItemFilterCheckboxBinding,
        private val listener: OnChooseListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EventFilterCheckBox) {

            binding.checkboxEventFilter.isChecked = item.isChecked
            binding.checkboxEventFilter.text = item.itemName

            binding.checkboxEventFilter.setOnClickListener {
                val newItem = EventFilterCheckBox(
                    positionId = adapterPosition,
                    itemName = item.itemName,
                    isChecked = !item.isChecked,
                    item.filter
                )
                listener.onClick(newItem, adapterPosition)
            }

        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<EventFilterCheckBox>() {
        override fun areItemsTheSame(
            oldItem: EventFilterCheckBox,
            newItem: EventFilterCheckBox
        ): Boolean {
            return oldItem.positionId == newItem.positionId
        }

        override fun areContentsTheSame(
            oldItem: EventFilterCheckBox,
            newItem: EventFilterCheckBox
        ): Boolean {
            return oldItem == newItem
        }
    }
}