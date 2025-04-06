package com.example.today

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.today.ui.theme.TodayTheme
import com.example.today.ui.theme.TodayTheme
import androidx.compose.material3.*
import com.example.today.compose.SettingsScreen
import com.example.today.infra.checkTheme
import com.example.today.infra.localeChecker
import com.example.today.room.AppDatabase

class SettingActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val db: AppDatabase = AppDatabase.getInstance(this)
        val categoryDao = db.CategoryDao()
        val categoryViewModel = CategoryViewModel(categoryDao)
        localeChecker(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodayTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    SettingsScreen(viewModel = categoryViewModel)
                }
            }
        }
    }
}
