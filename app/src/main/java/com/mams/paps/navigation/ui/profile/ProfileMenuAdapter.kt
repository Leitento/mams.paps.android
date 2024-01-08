package com.mams.paps.navigation.ui.profile

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mams.paps.databinding.ItemProfileMenuBinding

class ProfileMenuAdapter(
    private val clickListener: ProfileMenuItemClickListener
) : ListAdapter<ProfileMenuItem, ProfileMenuViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileMenuViewHolder {
        val binding = ItemProfileMenuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProfileMenuViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ProfileMenuViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<ProfileMenuItem>() {
        override fun areItemsTheSame(oldItem: ProfileMenuItem, newItem: ProfileMenuItem): Boolean =
            oldItem.titleResId == newItem.titleResId

        override fun areContentsTheSame(
            oldItem: ProfileMenuItem,
            newItem: ProfileMenuItem
        ): Boolean = oldItem == newItem
    }
}

class ProfileMenuViewHolder(
    val binding: ItemProfileMenuBinding,
    private val clickListener: ProfileMenuItemClickListener
) : ViewHolder(binding.root) {

    fun bind(item: ProfileMenuItem) {
        val context = itemView.context

        with(binding) {
            itemView.setOnClickListener {
                clickListener.onClick(item, adapterPosition)
            }

            title.setText(item.titleResId)
            icon.setImageResource(item.iconResId)

            val iconBackgroundColor = context.getColor(item.backgroundColorResId)
            iconBackground.backgroundTintList = ColorStateList.valueOf(iconBackgroundColor)
        }
    }
}
