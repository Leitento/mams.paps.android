package com.gomaping.navigation.ui.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.databinding.ItemCategoriesBinding

class MapCategoriesAdapter(private val categories: List<String>) :
    RecyclerView.Adapter<MapCategoriesAdapter.ViewHolder>() {

    class ViewHolder(
        val binding: ItemCategoriesBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(categories: String) {
            binding.textFilter.text = categories
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoriesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = categories[position]
        holder.bind(item)
    }
}