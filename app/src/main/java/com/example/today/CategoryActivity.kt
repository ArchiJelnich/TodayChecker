package com.example.today

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.preference.PreferenceManager
import com.example.today.compose.AddNameScreen
import com.example.today.compose.GraphScreen
import com.example.today.infra.flagGet
import com.example.today.infra.flagPut
import com.example.today.infra.localeChecker
import com.example.today.infra.pairToString
import com.example.today.infra.stringToPair
import com.example.today.room.AppDatabase
import com.example.today.room.Category
import com.example.today.room.DateInfo
import com.example.today.ui.theme.TodayTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CategoryActivity: ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        localeChecker(this)
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            TodayTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AddNameScreen()
                }
            }
        }
    }

    override fun onPause() {


        if (flagGet(this)==100)
        {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val categoryInfo = preferences.getString("category_new_info", ":")
        var categoryPair = categoryInfo?.let { stringToPair(it) }
        val categoryPairName = categoryPair?.first.toString()
        val categoryPairColor = categoryPair?.second?.toLong()

        val db: AppDatabase = AppDatabase.getInstance(this)
        val categoryDao = db.CategoryDao()
        GlobalScope.launch {
            val categoryToAdd = Category(
                cID = 0,
                cName = categoryPairName,
                cColor = categoryPairColor,
                deletedFlag = false
            )
            categoryDao.insertAll(categoryToAdd)
        }

            flagPut(this, 0)
    }

        super.onPause()
    }
}