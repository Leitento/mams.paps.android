package com.gomaping.navigation.ui.map.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
                null -> {
                    val colorStateList = ColorStateList.valueOf(Color.WHITE)
                    cardFilter.backgroundTintList = colorStateList
                    textFilter.setTextColor(Color.BLACK)
                    ivDelete.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
                    textFilter.text = DELETE_ALL
                }
                MapFilter.RATING -> { textFilter.text = RATING }
                MapFilter.AGE_GROUP -> { textFilter.text = AGE_GROUP }
                MapFilter.EQUIPMENT -> { textFilter.text = EQUIPMENT }
                MapFilter.SIZE-> { textFilter.text = SIZE }
                MapFilter.TOILET -> { textFilter.text = TOILET }
                MapFilter.CANOPY -> { textFilter.text = CANOPY }
                MapFilter.BENCHES-> { textFilter.text = BENCHES }
                MapFilter.COVERAGE-> { textFilter.text = COVERAGE }
                MapFilter.EQUIPMENT_MGN-> { textFilter.text = EQUIPMENT_MGN }
            }
            binding.ivDelete.setOnClickListener {
                onMapClickListener.OnDelete(adapterPosition)
            }
        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<MapFilter>() {
        override fun areItemsTheSame(oldItem: MapFilter, newItem: MapFilter): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MapFilter, newItem: MapFilter): Boolean {
            return true
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