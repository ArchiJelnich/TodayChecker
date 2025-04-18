package com.example.today.compose

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.preference.PreferenceManager
import com.example.today.CategoryActivity
import com.example.today.CategoryViewModel
import com.example.today.R
import com.example.today.SettingActivity
import com.example.today.infra.checkTheme
import com.example.today.infra.flagPut
import com.example.today.infra.loadLanguage
import com.example.today.infra.loadTheme
import com.example.today.infra.saveLanguage
import com.example.today.infra.saveTheme
import com.example.today.infra.setAppLocale
import com.example.today.infra.toColor
import com.example.today.room.AppDatabase
import com.example.today.room.AverageInfo
import com.example.today.room.Category
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen(viewModel: CategoryViewModel) {

    
    
    val context = LocalContext.current
    val startLocale = loadLanguage(context)
    var statsLocaleBool = true
    val startTheme = loadTheme(context)
    var statsThemeBool = true

    if (startLocale != "RU")
    {
        statsLocaleBool = false
    }

    if (startTheme != "light")
    {
        statsThemeBool = false
    }

    val colors = if (!statsThemeBool) {
        darkColorScheme(
            primary = Color.White,
            background = colorResource(R.color.dark_grey),
            onBackground = Color.White
        )
    } else {
        lightColorScheme(
            primary = colorResource(R.color.dark_grey),
            background = Color.White,
            onBackground = colorResource(R.color.dark_grey)
        )
    }



    MaterialTheme(
        colorScheme = colors
    ){
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.str_settings),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 40.dp)
                .padding(bottom = 40.dp),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp)
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.str_lan))
            Spacer(modifier = Modifier.weight(1f))
            var isRussian by remember { mutableStateOf(statsLocaleBool) }
            Switch(
                checked = isRussian,
                onCheckedChange = {
                    isRussian = it
                    var locale = "ENG"

                    if (isRussian){
                        locale = "RU"
                    }


                    setAppLocale(context, locale)
                    saveLanguage(context, locale)
                    Log.d("LangSwitch", "Switch is now: $it")
                }
            )
            Text(text = if (isRussian) {
                stringResource(R.string.str_ru)
            } else {
                stringResource(R.string.str_eng)
            }, modifier = Modifier.padding(start = 8.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp)
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.str_theme))
            Spacer(modifier = Modifier.weight(1f))
            var isLightTheme by remember { mutableStateOf(statsThemeBool) }
            Switch(
                checked = isLightTheme,
                onCheckedChange = {
                    isLightTheme = it
                    var theme = "dark"

                    if (isLightTheme){
                        theme = "light"
                    }

                    saveTheme(context, theme)
                    val activity = context as? android.app.Activity
                    activity?.recreate()
                }
            )
            Text(text = if (isLightTheme) stringResource(R.string.str_light) else stringResource(R.string.str_dark), modifier = Modifier.padding(start = 8.dp))
        }

        //val categories = listOf("Work", "Study", "Exercise", "Relax", "Hobby", "1", "2", "3", "4", "123", "sdas", "as") // TEMPORARY ZAGLUSHKA
        val categories by viewModel.categories.collectAsState(initial = emptyList())


        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            items(categories) { category ->
                CategoryItemWithActions(category = category)
            }
        }

        Button(
            onClick = {
                val intent = Intent(context, CategoryActivity::class.java)
                intent.putExtra("category_state", "new")
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.str_add_categ))
        }


        DeleteAllWithDialog(viewModel = viewModel)


        BottomPanel()
    }}}}


@Composable
fun CategoryItemWithActions(category: Category) {
    val context = LocalContext.current
    val circleColor = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f)
    var newColor = Color.White
    category.cColor?.let{ newColor = category.cColor.toColor()}

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(newColor)
        )

        category.cName?.let { Text(text = it, modifier = Modifier.padding(start = 16.dp)) }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category_state", "edit")
            intent.putExtra("category", category)
            context.startActivity(intent)
        }) {
            Icon(Icons.Default.Edit, contentDescription = "Edit")
        }


    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun DeleteAllWithDialog(viewModel: CategoryViewModel) {
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }

    Button(
        onClick = {
            showDialog = true
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
    ) {
        Text(stringResource(R.string.str_delete_all))
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.str_delete_all_q)) },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.str_no))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    viewModel.deleteAll()

                    val db: AppDatabase = AppDatabase.getInstance(context)
                    val averageDao = db.AverageDao()
                    val dateInfoDao = db.DateInfoDao()

                    GlobalScope.launch  {
                        averageDao.deleteAll()
                        dateInfoDao.deleteAll()
                    }


                    /*
                    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
                    val editor = preferences.edit()
                    editor.apply()
                    flagPut(context, 500)
                    val intent = Intent(context, SettingActivity::class.java)
                    preferences.edit().putInt("cID", passedID).apply()
                    context.startActivity(intent)*/

                }) {
                    Text(stringResource(R.string.str_OK))
                }
            },

            )
    }
}