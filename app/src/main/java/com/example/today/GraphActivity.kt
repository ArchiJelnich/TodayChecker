package com.example.today

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.today.ui.theme.TodayTheme
import androidx.compose.material3.*
import com.example.today.compose.GraphScreen
import com.example.today.infra.AverageByMonths
import com.example.today.infra.AverageForMonths
import com.example.today.infra.AverageForWeek
import com.example.today.infra.dateToString
import com.example.today.room.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate

class GraphActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodayTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    GraphScreen()
                }
            }
        }


        val db: AppDatabase = AppDatabase.getInstance(this)
        val dateInfoDao = db.DateInfoDao()

            /* GlobalScope.launch {
            Log.d("MyDebug", "Average YEAR =" + AverageByMonths(dateInfoDao, 2025))
            Log.d("MyDebug", "Average MONTH =" + AverageForMonths(dateInfoDao, 5, 2025))
            val dateToday = LocalDate.now()
            Log.d("MyDebug", "Average WEEK =" + AverageForWeek(dateInfoDao, dateToday))
        }
*/

    }
}
