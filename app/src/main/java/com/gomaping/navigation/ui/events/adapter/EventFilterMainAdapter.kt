package com.gomaping.navigation.ui.events.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.R
import com.gomaping.databinding.ItemFilterMenuBinding
import com.gomaping.navigation.ui.events.model.EventFilterMain
import com.gomaping.navigation.ui.events.model.Filter
import com.yandex.runtime.Runtime.getApplicationContext

interface OnItemClickListener {
    fun onItemClick(filter: Filter)
}

class EventFilterMainAdapter(
    private val listener: OnItemClickListener,
    private val data: List<EventFilterMain>) :
    RecyclerView.Adapter<EventFilterMainAdapter.EventFilterMainViewHolder>() {
    private var selectedPosition = 0
    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventFilterMainViewHolder {
        val binding = ItemFilterMenuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventFilterMainViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: EventFilterMainViewHolder, position: Int) {
        val item = data[position]
        val theme = getApplicationContext().theme
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

    inner class EventFilterMainViewHolder(
        val binding: ItemFilterMenuBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EventFilterMain) {
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

