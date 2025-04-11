package com.example.today.infra

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.today.R

fun loadTheme(context: Context): String? {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    return preferences.getString("theme_code", "light")
}

fun checkTheme(context: Context){
    if (loadTheme(context)=="light")
    {
        context.setTheme(R.style.Theme_Light)
    }
    else {
        context.setTheme(R.style.Theme_Dark)
    }
}

fun saveTheme(context: Context, themeCode: String) {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    preferences.edit().putString("theme_code", themeCode).apply()

}