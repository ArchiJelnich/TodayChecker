package com.example.today

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.today.compose.TodayScreen
import com.example.today.infra.DateToString
import com.example.today.infra.flagGet
import com.example.today.infra.flagJSON
import com.example.today.infra.flagPut
import com.example.today.infra.getMapFromSharedPreferences
import com.example.today.infra.lastDateGet
import com.example.today.infra.lastDatePut
import com.example.today.infra.parseMapFromString
import com.example.today.room.AppDatabase
import com.example.today.room.Category
import com.example.today.room.CategoryDao
import com.example.today.room.DateInfo
import com.example.today.ui.theme.TodayTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class TodayActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CoroutineCreationDuringComposition")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val db: AppDatabase = AppDatabase.getInstance(this)
        val dateInfoDao = db.DateInfoDao()
        var date_asset = 0


        val extras = intent.extras
        if (extras != null) {
            date_asset =  intent.extras?.get("asset") as Int
        }

        val dateToday = DateToString(LocalDate.now().plusDays(date_asset.toLong()))
        var dateText = getString(R.string.str_today)

        if (date_asset!=0) {
            dateText = dateToday
        }

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()

        GlobalScope.launch  {
            val dateInfo = dateInfoDao.getDateInfoByDay(dateToday)
            editor.putString("date_info_today", dateInfo)
            editor.apply()
        }

        Log.d("MyDebug", "onCreate : Asset " + date_asset)
        Log.d("MyDebug", "onCreate : Date " + dateText)


        setContent {
            TodayTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    //val date_info_map = getMapFromSharedPreferences(this)




                    val dateInfo = preferences.getString("date_info_today", "{}")
                    Log.d("MyDebug", "onCreate : dateInfo " + dateInfo)
                    val categoryDao = db.CategoryDao()
                    val categoryViewModel = CategoryViewModel(categoryDao)


                    if (lastDateGet(this)!=dateToday) {
                        flagJSON(this)
                    }

                    flagPut(this, 0)
                    if (dateInfo != null) {
                        TodayScreen(viewModel = categoryViewModel, dateInfo, date_asset, dateText)
                    }


                }
            }
        }






        //val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        //var spLanguage = preferences.getString("language", "eng")
        //val spTheme = preferences.getString("theme", "black")
        val spNewPage = preferences.getInt("new_page", 1)
        //val spEdited = preferences.getInt("edited", 0)
        if (spNewPage == 1)
        {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder
                .setMessage(getString(R.string.str_body))
                .setTitle(getString(R.string.str_header))
                .setPositiveButton(R.string.str_OK) { _, _ ->
                    // No action
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
            preferences.edit().putInt("new_page", 0).apply()
        }




        /*
        val today = LocalDate.now()
        val today_to_string = DateToString(today)
        val today_back_to_date = StrringToDate(today_to_string)
        val test_obj = today_back_to_date.plusDays(38)

        Log.d("MyDebug", today.toString())
        Log.d("MyDebug", today_to_string.toString())
        Log.d("MyDebug", today_back_to_date.toString())
        Log.d("MyDebug", test_obj.toString())

*/

        /*
        Log.d("MyDebug", sp_language + " " + sp_theme + " " + sp_new_page + " " + sp_edited)
        preferences.edit().putString("language", "ru").apply()
        sp_language = preferences.getString("language", "eng")
        Log.d("MyDebug", sp_language + " " + sp_theme + " " + sp_new_page + " " + sp_edited)

         */

/*
             val db: AppDatabase = AppDatabase.getInstance(this)

         val categoryDao = db.CategoryDao()
         GlobalScope.launch  {
             val category = Category(
                 cID = 0,
                 cName = "Play",
                 cColor = 0xFFFF0000,
                 deletedFlag = false
             )
             val category_1 = Category(
                 cID = 0,
                 cName = "Read",
                 cColor = 0xFFFFF000,
                 deletedFlag = false
             )
             val category_2 = Category(
                 cID = 0,
                 cName = "Sport",
                 cColor = 0xFFFF3400,
                 deletedFlag = false
             )
             val category_3 = Category(
                 cID = 0,
                 cName = "Dance",
                 cColor = 0xFFFF0000,
                 deletedFlag = true
             )

             categoryDao.deleteAll()
             categoryDao.insertAll(category)
             categoryDao.insertAll(category_1)
             categoryDao.insertAll(category_2)
             categoryDao.insertAll(category_3)
             Log.d("MyDebug", categoryDao.getAll().toString())

}
        */




 }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPause() {
        super.onPause()
        val edit_flag = flagGet(this)
        if (edit_flag==0)
        {

        }
        else         {
            val db: AppDatabase = AppDatabase.getInstance(this)
            val dateInfoDao = db.DateInfoDao()
            var date_asset = 0
            val extras = intent.extras
            if (extras != null) {
                date_asset =  intent.extras?.get("asset") as Int
            }
            val dateToday = DateToString(LocalDate.now().plusDays(date_asset.toLong()))
            var dateInfo_map = getMapFromSharedPreferences(this)
            var dateInfo = getMapFromSharedPreferences(this).toString()

            Log.d("MyDebug", "onPause : Asset " + date_asset)
            Log.d("MyDebug", "onPause : Date " + dateToday)
            Log.d("MyDebug", "onPause : dateInfo " + dateInfo)

            GlobalScope.launch {

                val DateExist = dateInfoDao.getIDByDay(dateToday)
                if (DateExist!=0) {
                    Log.d("MyDebug", "onPause : UPDATE")
                    val old_info = parseMapFromString(dateInfoDao.getDateInfoByDay(dateToday))
                    Log.d("MyDebug", "onPause : old_info" + old_info)
                    Log.d("MyDebug", "onPause : dateInfo_map" + dateInfo_map)

                    val dateInfo_map_merged = dateInfo_map + old_info.filterKeys { it !in dateInfo_map }



                    Log.d("MyDebug", "onPause : dateInfo_map_merged" + dateInfo_map_merged)

                    dateInfoDao.update(date_info = dateInfo_map_merged.toString(), date = dateToday)
                }
                else
                {
                    Log.d("MyDebug", "onPause : New Record")
                    val dateInfo_new = DateInfo(
                        dID = 0,
                        date = dateToday,
                        date_info = dateInfo
                    )
                    dateInfoDao.insert(dateInfo_new)
                }
            }



        }}}




@RequiresApi(Build.VERSION_CODES.O)
class CategoryViewModel(private val dao: CategoryDao) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    init {
        viewModelScope.launch {
            _categories.value = dao.getNotDeleted()
        }

    }

}


