package com.gomaping.navigation.ui.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.databinding.ItemFilterCheckboxBinding
import com.gomaping.navigation.ui.events.model.EventFilterCheckBox

class EventFilterCheckBoxAdapter(private val data: List<EventFilterCheckBox>) :
    RecyclerView.Adapter<EventFilterCheckBoxAdapter.EventFilterViewHolder>() {
    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventFilterViewHolder {
        val binding = ItemFilterCheckboxBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventFilterViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class EventFilterViewHolder(
        val binding: ItemFilterCheckboxBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(filter: EventFilterCheckBox) {
            binding.filterTitle.text = filter.item
        }
    }
}