package com.gomaping.navigation.ui.profile.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gomaping.databinding.ItemProfileMenuBinding
import com.gomaping.navigation.ui.profile.ProfileMenuItem
import com.gomaping.navigation.ui.profile.ProfileMenuItemClickListener

class ProfileMenuAdapter(
    private val clickListener: ProfileMenuItemClickListener
) : RecyclerView.Adapter<ProfileMenuViewHolder>() {

    var items: List<ProfileMenuItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileMenuViewHolder {
        val binding = ItemProfileMenuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProfileMenuViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ProfileMenuViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
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
