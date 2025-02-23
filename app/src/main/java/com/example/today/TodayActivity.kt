package com.example.today

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.today.ui.theme.TodayTheme
import androidx.compose.material3.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.today.compose.TodayScreen
import com.example.today.room.AppDatabase
import com.example.today.room.Category
import com.example.today.room.CategoryDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodayActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodayTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    val db: AppDatabase = AppDatabase.getInstance(this)
                    val categoryDao = db.CategoryDao()
                    val categoryViewModel = CategoryViewModel(categoryDao)

                    GlobalScope.launch  {
                        Log.d("MyDebug", categoryDao.getAll().toString())
                        }

                    TodayScreen(viewModel = categoryViewModel)
                }
            }
        }

        /*

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        var sp_language = preferences.getString("language", "eng")
        val sp_theme = preferences.getString("theme", "black")
        val sp_new_page = preferences.getString("new_page", "1")
        val sp_edited = preferences.getString("edited", "0")
        Log.d("MyDebug", sp_language + " " + sp_theme + " " + sp_new_page + " " + sp_edited)
        preferences.edit().putString("language", "ru").apply()
        sp_language = preferences.getString("language", "eng")
        Log.d("MyDebug", sp_language + " " + sp_theme + " " + sp_new_page + " " + sp_edited)

         */


            /* val db: AppDatabase = AppDatabase.getInstance(this)

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
             Log.d("MyDebug", categoryDao.getAll().toString())

}


*/

 }



}



class CategoryViewModel(private val dao: CategoryDao) : ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    init {
        viewModelScope.launch {
            _categories.value = dao.getNotDeleted()
        }
    }
}