package com.mams.paps.navigation.ui.map

import androidx.lifecycle.ViewModel
import com.mams.paps.navigation.model.MapCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapViewModel : ViewModel() {

    private val _selectedCategory = MutableStateFlow(MapCategory.NONE)
    val selectedCategory = _selectedCategory.asStateFlow()

    fun setCategory(category: MapCategory) {
        _selectedCategory.value = category
    }
}
