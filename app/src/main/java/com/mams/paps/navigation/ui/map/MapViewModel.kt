package com.mams.paps.navigation.ui.map

import androidx.lifecycle.ViewModel
import com.mams.paps.navigation.model.MapCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapViewModel : ViewModel() {

    private val _selectedCategory: MutableStateFlow<MapCategory?> = MutableStateFlow(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    fun setCategory(category: MapCategory? = null) {
        _selectedCategory.value = category
    }
}
