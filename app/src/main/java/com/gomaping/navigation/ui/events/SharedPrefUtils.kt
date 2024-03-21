package com.gomaping.navigation.ui.events

import android.content.Context
import com.gomaping.navigation.ui.events.model.EventFilterCheckBox
import com.gomaping.navigation.ui.events.model.Filter
import com.gomaping.navigation.ui.map.model.MapFilter
import com.gomaping.navigation.ui.map.model.MapFilterCheckBox
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SharedPrefUtils(private val context: Context) {
    companion object {
        private const val PREF_FILE_NAME = "my_preferences"
        private const val KEY_EVENT_FILTERS = "event_filters"
        private const val KEY_MAP_FILTERS = "map_filters"
    }
    fun saveEventFilters(filters: Map<Filter, MutableList<EventFilterCheckBox>>) {
        val sharedPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(filters)
        sharedPref.edit().putString(KEY_EVENT_FILTERS, json).apply()
    }

    fun loadEventFilters(): Map<Filter, MutableList<EventFilterCheckBox>> {
        val sharedPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val json = sharedPref.getString(KEY_EVENT_FILTERS, "") ?: ""
        val type = object : TypeToken<HashMap<Filter, MutableList<EventFilterCheckBox>>>() {}.type
        return Gson().fromJson(json, type) ?: mapOf()
    }

    fun saveMapFilters(filters: Map<MapFilter, MutableList<MapFilterCheckBox>>) {
        val sharedPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(filters)
        sharedPref.edit().putString(KEY_MAP_FILTERS, json).apply()
    }

    fun loadMapFilters(): Map<MapFilter, MutableList<MapFilterCheckBox>> {
        val sharedPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val json = sharedPref.getString(KEY_MAP_FILTERS, "") ?: ""
        val type = object : TypeToken<HashMap<MapFilter, MutableList<MapFilterCheckBox>>>() {}.type
        return Gson().fromJson(json, type) ?: mapOf()
    }

    fun ClearAll(){
        val sharedPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}