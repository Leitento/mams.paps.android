package com.mams.paps.common.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mams.paps.databinding.ItemActionButtonBinding

class ActionButtonAdapter(
    private val itemHeight: Int,
    private val buttonClickListener: (id: String) -> Unit
) : ListAdapter<ActionButton, ActionViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val binding = ItemActionButtonBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ActionViewHolder(binding, itemHeight, buttonClickListener)
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        val action = getItem(position)
        holder.bind(action)
    }

    class DiffCallback : DiffUtil.ItemCallback<ActionButton>() {
        override fun areItemsTheSame(oldItem: ActionButton, newItem: ActionButton): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ActionButton, newItem: ActionButton): Boolean {
            return oldItem == newItem
        }
    }
}

class ActionViewHolder(
    private val binding: ItemActionButtonBinding,
    private val itemHeight: Int,
    private val buttonClickListener: (id: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.updateLayoutParams {
            height = itemHeight
        }
    }

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
