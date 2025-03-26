package com.example.today.infra

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceManager
import java.time.LocalDate

fun flagGet(context: Context): Int {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    val editFlag = preferences.getInt("edit_flag", 0)
    return editFlag
}

fun flagPut(context: Context, value : Int)  {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    preferences.edit().putInt("edit_flag", value).apply()
}

fun flagJSON(context: Context)  {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    preferences.edit().putString("category_counts", "{}").apply()
}

fun lastDateGet(context: Context) : String? {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    val lastDate = preferences.getString("last_date", "0.0.0000")
    return lastDate
}

@RequiresApi(Build.VERSION_CODES.O)
fun lastDatePut(context: Context)  {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    val today = dateToString(LocalDate.now())
    preferences.edit().putString("last_date", today).apply()
}