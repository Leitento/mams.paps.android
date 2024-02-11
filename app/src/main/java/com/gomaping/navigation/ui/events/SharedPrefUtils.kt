package com.gomaping.navigation.ui.events

import android.content.Context
import com.gomaping.navigation.ui.events.model.EventFilterCheckBox
import com.gomaping.navigation.ui.events.model.Filter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object SharedPrefUtils {
    private const val PREF_FILE_NAME = "my_preferences"
    private const val KEY_FILTERS = "filters"

    fun saveFilters(context: Context, filters: Map<Filter, MutableList<EventFilterCheckBox>>) {
        val sharedPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(filters)
        sharedPref.edit().putString(KEY_FILTERS, json).apply()
    }

    fun loadFilters(context: Context): Map<Filter, MutableList<EventFilterCheckBox>> {
        val sharedPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val json = sharedPref.getString(KEY_FILTERS, "") ?: ""
        val type = object : TypeToken<HashMap<Filter, MutableList<EventFilterCheckBox>>>() {}.type
        return Gson().fromJson(json, type) ?: mapOf()
    }

    fun ClearAll(context: Context){
        val sharedPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}