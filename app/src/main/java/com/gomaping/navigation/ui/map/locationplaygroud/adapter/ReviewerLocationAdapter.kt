package com.gomaping.navigation.ui.map.locationplaygroud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gomaping.R
import com.gomaping.databinding.ItemLocationFilterBinding
import com.gomaping.navigation.ui.map.adapter.MapFilterAdapter
import com.gomaping.navigation.ui.map.model.MapFilter
import com.gomaping.navigation.ui.map.model.MapFilterCheckBox

class ReviewerLocationAdapter(private val map: Map<MapFilter, MutableList<MapFilterCheckBox>>) :
    RecyclerView.Adapter<ReviewerLocationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemLocationFilterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mapFilterEntry = map.entries.elementAt(position)
        holder.bind(mapFilterEntry)
    }

    override fun getItemCount(): Int = map.size

    inner class ViewHolder(
        val binding: ItemLocationFilterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mapFilterEntry: Map.Entry<MapFilter, MutableList<MapFilterCheckBox>>) =
            with(binding) {
                when (mapFilterEntry.key) {
                    MapFilter.RATING -> {}

                    MapFilter.AGE_GROUP -> {
                        filterCategories.setImageResource(mapFilterEntry.key.image)
                        nameFilterCategories.text = MapFilterAdapter.AGE_GROUP
                        itemFilterCategories.text = bindFilter(mapFilterEntry.value)
                    }

                    MapFilter.EQUIPMENT -> {
                        filterCategories.setImageResource(mapFilterEntry.key.image)
                        nameFilterCategories.text = MapFilterAdapter.EQUIPMENT
                        itemFilterCategories.text = bindFilter(mapFilterEntry.value)
                    }

                    MapFilter.BENCHES -> {
                        nameFilterCategories.text = MapFilterAdapter.BENCHES
                        val text = bindFilter(mapFilterEntry.value)
                        if (text == NO) {
                            filterCategories.setImageResource(R.drawable.ic_image_banches_grey)
                        } else {
                            filterCategories.setImageResource(mapFilterEntry.key.image)
                        }
                        itemFilterCategories.text = text
                    }

                    MapFilter.CANOPY -> {
                        nameFilterCategories.text = MapFilterAdapter.CANOPY
                        val text = bindFilter(mapFilterEntry.value)
                        if (text == NO) {
                            filterCategories.setImageResource(R.drawable.ic_image_canopy_grey)
                        } else {
                            filterCategories.setImageResource(mapFilterEntry.key.image)
                        }
                        itemFilterCategories.text = text
                    }

                    MapFilter.TOILET -> {
                        nameFilterCategories.text = MapFilterAdapter.TOILET
                        val text = bindFilter(mapFilterEntry.value)
                        if (text == NO) {
                            filterCategories.setImageResource(R.drawable.ic_image_toilet_gray)
                        } else {
                            filterCategories.setImageResource(mapFilterEntry.key.image)
                        }
                        itemFilterCategories.text = text
                    }

                    MapFilter.SIZE -> {
                        filterCategories.setImageResource(mapFilterEntry.key.image)
                        nameFilterCategories.text = MapFilterAdapter.SIZE
                        itemFilterCategories.text = bindFilter(mapFilterEntry.value)
                    }

                    MapFilter.COVERAGE -> {
                        filterCategories.setImageResource(mapFilterEntry.key.image)
                        nameFilterCategories.text = MapFilterAdapter.COVERAGE
                        itemFilterCategories.text = bindFilter(mapFilterEntry.value)
                    }

                    MapFilter.EQUIPMENT_MGN -> {
                        nameFilterCategories.text = MapFilterAdapter.EQUIPMENT_MGN
                        val text = bindFilter(mapFilterEntry.value)
                        if (text == NO) {
                            filterCategories.setImageResource(R.drawable.ic_image_equipment_mgh_grey)
                        } else {
                            filterCategories.setImageResource(mapFilterEntry.key.image)
                        }
                        itemFilterCategories.text = text
                    }

                }
            }
    }

    private fun bindFilter(valueList: MutableList<MapFilterCheckBox>): String {
        var text = ""
        if (valueList.isNotEmpty()) text = valueList[0].itemName
        if (valueList.size > 1) {
            for (filter in valueList.subList(1, valueList.size)) {
                if (filter.isChecked) text += ", ${filter.itemName}"
            }
        }
        return text
    }

    companion object {
        const val NO = "Нет"
    }
}