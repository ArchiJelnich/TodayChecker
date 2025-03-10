package com.example.today.infra

import android.content.Context
import androidx.preference.PreferenceManager

fun flagGet(context: Context): Int {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    val edit_flag = preferences.getInt("edit_flag", 0)
    return edit_flag
}

fun flagPut(context: Context, value : Int)  {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    preferences.edit().putInt("edit_flag", value).apply()
}

fun flagJSON(context: Context)  {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    preferences.edit().putString("category_counts", "{}").apply()
}