package com.example.today

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.today.ui.theme.TodayTheme
import com.example.today.ui.theme.TodayTheme
import androidx.compose.material3.*
import com.example.today.compose.SettingsScreen
import com.example.today.infra.localeChecker

class SettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        localeChecker(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodayTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    SettingsScreen()
                }
            }
        }
    }
}
