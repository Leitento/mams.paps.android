package com.gomaping.navigation.ui.events.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.databinding.ItemCardBinding
import com.gomaping.navigation.ui.events.model.Filter

interface OnClickListener {
    fun OnDelete(position: Int)
}

class FiltersAdapter(
    private val onClickListener: OnClickListener,
) : ListAdapter<Filter, FiltersAdapter.FilterViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FilterViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = getItem(position)
        holder.bind(filter)
    }

    class FilterViewHolder(
        private val binding: ItemCardBinding,
        private val onClickListener: OnClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Filter?) = with(binding) {
            when (item) {
                null -> {
                    val colorStateList = ColorStateList.valueOf(Color.WHITE)
                    cardFilter.backgroundTintList = colorStateList
                    textFilter.setTextColor(Color.BLACK)
                    ivDelete.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
                    textFilter.text = DELETE_ALL
                }
                Filter.RATING -> { textFilter.text = RATING }
                Filter.AGE_GROUP -> { textFilter.text = AGE_GROUP }
                Filter.EXHIBITIONS -> { textFilter.text = EXHIBITIONS }
                Filter.CONCERTS -> { textFilter.text = CONCERTS }
                Filter.THEATRE -> { textFilter.text = THEATRE }
                Filter.FESTIVALS -> { textFilter.text = FESTIVALS }
                Filter.SPORT -> { textFilter.text = SPORT }
            }
          binding.ivDelete.setOnClickListener {
              onClickListener.OnDelete(adapterPosition)
          }
        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<Filter>() {
        override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
            return true
        }
    }

    companion object {
        const val RATING = "Рейтинг"
        const val AGE_GROUP = "Возрастная группа"
        const val EXHIBITIONS = "Выставки"
        const val CONCERTS = "Концерты"
        const val THEATRE = "Театры"
        const val FESTIVALS = "Фестивали"
        const val SPORT = "Спорт"
        const val DELETE_ALL = "Очистить"
    }
}