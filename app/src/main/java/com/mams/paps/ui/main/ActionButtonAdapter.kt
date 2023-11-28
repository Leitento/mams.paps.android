package com.mams.paps.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mams.paps.databinding.ItemMainActionBinding

typealias ActionButtonClickListener = (id: String) -> Unit

class ActionButtonAdapter(
    private val buttonClickListener: ActionButtonClickListener
) : ListAdapter<ActionButton, ActionViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val binding = ItemMainActionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ActionViewHolder(binding, buttonClickListener)
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        val action = getItem(position)
        holder.bind(action)
    }

    class DiffCallback : DiffUtil.ItemCallback<ActionButton>() {
        override fun areItemsTheSame(oldItem: ActionButton, newItem: ActionButton): Boolean {
            return oldItem.titleResId == newItem.titleResId
        }

        override fun areContentsTheSame(oldItem: ActionButton, newItem: ActionButton): Boolean {
            return oldItem == newItem
        }
    }
}

class ActionViewHolder(
    private val binding: ItemMainActionBinding,
    private val buttonClickListener: ActionButtonClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(actionButton: ActionButton) {
        itemView.setOnClickListener {
            buttonClickListener(actionButton.id)
        }

        with(binding) {
            image.setImageResource(actionButton.imageResId)
            title.setText(actionButton.titleResId)
        }
    }
}
