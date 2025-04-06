package com.example.today.compose

import android.app.AlertDialog
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.preference.PreferenceManager
import com.example.today.R
import com.example.today.SettingActivity
import com.example.today.infra.flagPut
import com.example.today.infra.inputCheckerText
import com.example.today.infra.loadTheme
import kotlin.random.Random


@Composable
fun AddNameScreen() {
    var name by remember { mutableStateOf("") }
    val randomColor = remember { Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f) }
    val context = LocalContext.current
    val startTheme = loadTheme(context)
    var statsThemeBool = true


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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text =  stringResource(R.string.str_name),
            style = MaterialTheme.typography.headlineMedium
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text(stringResource(R.string.str_type)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(randomColor)
        )

        Button(
            onClick = {
                val checkerResult = inputCheckerText(name)
                if (checkerResult.second == 1)
                {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder
                        .setTitle(checkerResult.first)
                        .setPositiveButton(R.string.str_OK) { _, _ ->
                        }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
                else
                {
                    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
                    val editor = preferences.edit()
                    val categoryInfo = "${checkerResult.first}:0"
                    editor.putString("category_new_info", categoryInfo)
                    editor.apply()
                    flagPut(context, 100)
                    val intent = Intent(context, SettingActivity::class.java)
                    context.startActivity(intent)

                }

            },
            enabled = name.isNotBlank()
        ) {
            Text(stringResource(R.string.str_add))
        }
    }
}}}