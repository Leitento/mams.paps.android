package com.gomaping.navigation.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.gomaping.GoMapingApplication
import com.gomaping.navigation.ui.events.model.EventFilterCheckBox
import com.gomaping.navigation.ui.events.model.EventModel
import com.gomaping.navigation.ui.events.model.Filter
import com.gomaping.navigation.ui.events.repository.EventRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventsViewModel(
    private val sharedPrefUtils: SharedPrefUtils
) : ViewModel() {

    private val repositoryImpl = EventRepositoryImpl()
    private val _events = MutableStateFlow<List<EventModel>>(listOf())
    val events = _events.asStateFlow()

    private val checkedItems = mutableListOf<Filter?>()

    init {
        getAllEvent()
    }

    fun getFilterByPosition(position: Int): Filter? {
        return checkedItems[position]
    }

    fun clearFilter(){
        checkedItems.clear()
    }

    fun removeFilterAt(position: Int){
        checkedItems.removeAt(position)
    }

    fun getFilterSize() = checkedItems.size

    fun getFilter() = checkedItems.toList()

    fun addFilter(filter: Filter){
        checkedItems.add(filter)
    }

    fun addFilterToFirstPosition(){
        checkedItems.add(0, null)
    }

    private fun getAllEvent() {
        viewModelScope.launch {
            try {
                repositoryImpl.getEvents().collect {
                    _events.value = it
                }
            } catch (_: Exception) {
            }
        }
    }

    fun loadSharePref(): Map<Filter, MutableList<EventFilterCheckBox>> {
        return sharedPrefUtils.loadEventFilters()
    }

    fun saveSharedPref(filterMap: MutableMap<Filter, MutableList<EventFilterCheckBox>>) {
        sharedPrefUtils.saveEventFilters(filterMap)
    }

    fun clearAll() {
        sharedPrefUtils.ClearAll()
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val sharedPrefUtils =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GoMapingApplication).sharedPrefUtils
                EventsViewModel(
                    sharedPrefUtils = sharedPrefUtils
                )
            }
        }
    }
}