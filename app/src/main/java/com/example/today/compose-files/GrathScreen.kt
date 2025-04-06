package com.example.today.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.today.R
import com.example.today.infra.loadTheme
import kotlin.random.Random

@Composable
fun GraphScreen() {
    var selectedView by remember { mutableStateOf("week") }
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
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.str_grapth),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 40.dp)
                .padding(bottom = 40.dp),
            textAlign = TextAlign.Center
        )

        Row(modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.weight(1f)) {
                PeriodContent(selectedView)
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { selectedView = "week" }) { }
                Button(onClick = { selectedView = "month" }) { }
                Button(onClick = { selectedView = "year" }) { }
            }
        }

        BottomPanel()
    }
}}}

@Composable
fun PeriodContent(view: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /* TODO: Previous period */ }) {
                Text("<")
            }
            Text(text = when (view) {
                "week" -> stringResource(R.string.str_week)
                "month" -> stringResource(R.string.str_month)
                "year" -> stringResource(R.string.str_year)
                else -> ""
            },
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = { /* TODO: Next period */ }) {
                Text(">")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        PeriodGrid(view)
    }
}

@Composable
fun PeriodGrid(view: String) {
    val columns = when (view) {
        "week" -> 7
        "month" -> 31
        "year" -> 12
        else -> 0
    }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow {
            items((1..columns).toList()) { columnNumber ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    repeat(24) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(
                                    Color(
                                        Random.nextFloat(),
                                        Random.nextFloat(),
                                        Random.nextFloat(),
                                        1f
                                    )
                                )
                        )
                    }
                    Text(text = columnNumber.toString(), modifier = Modifier.padding(top = 4.dp))
                }
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPeriodScreen() {
    GraphScreen()
}