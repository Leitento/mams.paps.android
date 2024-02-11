package com.gomaping.navigation.ui.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.databinding.ItemEventBinding
import com.gomaping.navigation.ui.events.model.EventModel

class EventsAdapter(
    private val listener: (Long) -> Unit,
) : androidx.recyclerview.widget.ListAdapter<EventModel, EventsAdapter.EventViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class EventViewHolder(
        private val binding: ItemEventBinding,
        private val listener: (Long) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventModel) {
            binding.actionEvent.setOnClickListener {

            }

            with(binding) {
                titleEvent.text = event.title
                linkEvent.text = event.link
                descriptionEvent.text = event.descriptions
                scheduleEvent.text = event.schedule
                breakEvent.text = event.breakTime
                reviewsEvent.text = event.reviews
                carTimeEvent.text = event.timeTo
                distanceEvent.text = event.distance
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<EventModel>() {
        override fun areItemsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
            return oldItem == newItem
        }
    }
}