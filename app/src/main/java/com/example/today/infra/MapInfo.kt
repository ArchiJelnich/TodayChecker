package com.example.today.infra

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun saveMapToSharedPreferences(context: Context, map: MutableMap<Int, Int>) {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = preferences.edit()
    val json = Gson().toJson(map)
    editor.putString("category_counts", json)
    editor.apply()
}

fun getMapFromSharedPreferences(context: Context): MutableMap<Int, Int> {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    val json = preferences.getString("category_counts", "{}")
    val type = object : TypeToken<MutableMap<Int, Int>>() {}.type
    return Gson().fromJson(json, type)
}

fun updateMap(context: Context, key: Int, count : Int){
    val map = getMapFromSharedPreferences(context)
    map[key] = count
    saveMapToSharedPreferences(context, map)
}