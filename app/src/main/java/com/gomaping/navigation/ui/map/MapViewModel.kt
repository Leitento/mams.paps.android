package com.gomaping.navigation.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.gomaping.GoMapingApplication
import com.gomaping.navigation.model.MapCategory
import com.gomaping.navigation.ui.events.EventsViewModel
import com.gomaping.navigation.ui.events.SharedPrefUtils
import com.gomaping.navigation.ui.map.model.MapFilter
import com.gomaping.navigation.ui.map.model.MapFilterCheckBox
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapViewModel(
    private val sharedPrefUtils: SharedPrefUtils
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow(MapCategory.NONE)
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val checkedItems = mutableListOf<MapFilter?>()

    fun setCategory(category: MapCategory) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }


    fun getFilterByPosition(position: Int): MapFilter? {
        return checkedItems[position]
    }

    fun clearFilter() {
        checkedItems.clear()
    }

    fun removeFilterAt(position: Int) {
        checkedItems.removeAt(position)
    }

    fun getFilterSize() = checkedItems.size

    fun getFilter() = checkedItems.toList()

    fun addFilter(filter: MapFilter) {
        checkedItems.add(filter)
    }

    fun addFilterToFirstPosition() {
        checkedItems.add(0, null)
    }


    fun loadSharePref(): Map<MapFilter, MutableList<MapFilterCheckBox>> {
        return sharedPrefUtils.loadMapFilters()
    }

    fun saveSharedPref(filterMap: MutableMap<MapFilter, MutableList<MapFilterCheckBox>>) {
        sharedPrefUtils.saveMapFilters(filterMap)
    }

    fun clearAll() {
        sharedPrefUtils.ClearAll()
    }

    fun getListOfKeyIsNonEmpty(): List<MapFilter> {
        val map = loadSharePref()
        return map.filter { (_, value) ->
            value.any { it.isChecked }
        }.keys.toList()
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val sharedPrefUtils =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GoMapingApplication).sharedPrefUtils
                MapViewModel(
                    sharedPrefUtils = sharedPrefUtils
                )
            }
        }
    }
}
