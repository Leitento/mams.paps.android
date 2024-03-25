package com.gomaping.navigation.ui.map.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.R
import com.gomaping.databinding.ItemCardBinding
import com.gomaping.navigation.ui.map.model.MapFilter

interface OnMapClickListener {
    fun OnDelete(position: Int)
}
class MapFilterAdapter(
private val onMapClickListener: OnMapClickListener,
) : ListAdapter<MapFilter, MapFilterAdapter.FilterViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FilterViewHolder(binding, onMapClickListener)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = getItem(position)
        holder.bind(filter)
    }

    class FilterViewHolder(
        private val binding: ItemCardBinding,
        private val onMapClickListener: OnMapClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MapFilter?) = with(binding) {
            when (item) {
                MapFilter.RATING -> {
                    setColorItem(itemView.resources.getColor(R.color.red, null), Color.WHITE)
                    textFilter.text = RATING }
                MapFilter.AGE_GROUP -> {
                    setColorItem(itemView.resources.getColor(R.color.red, null), Color.WHITE)
                    textFilter.text = AGE_GROUP }
                MapFilter.EQUIPMENT -> {
                    setColorItem(itemView.resources.getColor(R.color.red, null), Color.WHITE)
                    textFilter.text = EQUIPMENT }
                MapFilter.SIZE-> {
                    setColorItem(itemView.resources.getColor(R.color.red, null), Color.WHITE)
                    textFilter.text = SIZE }
                MapFilter.TOILET -> {
                    setColorItem(itemView.resources.getColor(R.color.red, null), Color.WHITE)
                    textFilter.text = TOILET }
                MapFilter.CANOPY -> {
                    setColorItem(itemView.resources.getColor(R.color.red, null), Color.WHITE)
                    textFilter.text = CANOPY }
                MapFilter.BENCHES-> {
                    setColorItem(itemView.resources.getColor(R.color.red, null), Color.WHITE)
                    textFilter.text = BENCHES }
                MapFilter.COVERAGE-> {
                    setColorItem(itemView.resources.getColor(R.color.red, null), Color.WHITE)
                    textFilter.text = COVERAGE }
                MapFilter.EQUIPMENT_MGN-> {
                    setColorItem(itemView.resources.getColor(R.color.red, null), Color.WHITE)
                    textFilter.text = EQUIPMENT_MGN }
                null -> {
                    setColorItem(Color.WHITE, Color.BLACK)
                    textFilter.text = DELETE_ALL}
            }
            binding.ivDelete.setOnClickListener {
                onMapClickListener.OnDelete(adapterPosition)
            }
        }
        private fun setColorItem(backgroundColor: Int, textColor: Int) = with(binding){
            val colorStateList = ColorStateList.valueOf(backgroundColor)
            cardFilter.backgroundTintList = colorStateList
            textFilter.setTextColor(textColor)
            ivDelete.setColorFilter(textColor, PorterDuff.Mode.SRC_IN)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<MapFilter>() {
        override fun areItemsTheSame(oldItem: MapFilter, newItem: MapFilter): Boolean {
            return oldItem.image == newItem.image
        }

        override fun areContentsTheSame(oldItem: MapFilter, newItem: MapFilter): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val RATING = "Рейтинг"
        const val AGE_GROUP = "Возрастная группа"
        const val EQUIPMENT = "Оборудование"
        const val SIZE = "Размер"
        const val TOILET = "Туалет"
        const val CANOPY = "Навес"
        const val BENCHES = "Лавочки"
        const val COVERAGE = "Покрытие"
        const val EQUIPMENT_MGN = "Оборудование МГН"
        const val DELETE_ALL = "Очистить"
    }
}