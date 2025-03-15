package com.example.today.compose

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.today.CategoryViewModel
import com.example.today.R
import com.example.today.infra.flagPut
import com.example.today.infra.parseMapFromString
import com.example.today.infra.updateMap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayScreen(viewModel: CategoryViewModel, date_info : String) {


    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.str_today),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 40.dp)
                .padding(bottom = 40.dp),
            textAlign = TextAlign.Center
        )

        //val categories_old = listOf("Work", "Study", "Exercise", "Relax", "Hobby", "1", "2", "3", "4", "123", "sdas", "as") // TEMPORARY ZAGLUSHKA
        val categories by viewModel.categories.collectAsState()

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(categories) { category ->
                category.cName?.let {
                    category.cColor?.let { it1 ->
                        CategoryItem(
                            cName = it,
                            cColor = it1,
                            cID = category.cID,
                            viewModel = viewModel,
                            dateInfo = date_info
                        )
                    }
                }
            }
        }

        BottomPanel()
    }


}

@Composable
fun CategoryItem(cName: String, cColor: Long, viewModel: CategoryViewModel, cID: Int, dateInfo : String) {

    var initialCount = 0

    if (dateInfo != "{}") {
        val dateInfMap = parseMapFromString(dateInfo)
        if (dateInfMap[cID]!=null)
        {
            initialCount = dateInfMap[cID]!!
        }
    }



    //Log.d("MyDebug", " -- CategoryItem -- " + cID + " " + initialCount)

    var count by remember { mutableIntStateOf(initialCount) }
    val circleColor = Color(cColor)
    val context = LocalContext.current
    //val circleColor = Color(cColor.toLong())


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp)
            .padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(circleColor),
        )

        Text(text = cName, modifier = Modifier.padding(start = 16.dp))
        Spacer(modifier = Modifier.weight(1f))



        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { if (count > 0) { count--; flagPut(context, 1); updateMap(context, cID, count) } }) { Text("-") }
            Text(text = count.toString(), modifier = Modifier.padding(horizontal = 8.dp))
            Button(onClick = { count++; flagPut(context, 1); updateMap(context, cID, count) }) { Text("+") }
        }
    }

}






////////////////////////////////



@Composable
fun SettingsScreen() {
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
            var isRussian by remember { mutableStateOf(true) }
            Switch(
                checked = isRussian,
                onCheckedChange = { isRussian = it }
            )
            Text(text = if (isRussian) stringResource(R.string.str_ru) else stringResource(R.string.str_eng), modifier = Modifier.padding(start = 8.dp))
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
            var isLightTheme by remember { mutableStateOf(true) }
            Switch(
                checked = isLightTheme,
                onCheckedChange = { isLightTheme = it }
            )
            Text(text = if (isLightTheme) stringResource(R.string.str_light) else stringResource(R.string.str_dark), modifier = Modifier.padding(start = 8.dp))
        }

        val categories = listOf("Work", "Study", "Exercise", "Relax", "Hobby", "1", "2", "3", "4", "123", "sdas", "as") // TEMPORARY ZAGLUSHKA
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
            onClick = { /* TODO: Add category */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.str_add_categ))
        }

        Button(
            onClick = { /* TODO: Delete all data */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(stringResource(R.string.str_delete_all), color = Color.White)
        }


        BottomPanel()
    }}


@Composable
fun CategoryItemWithActions(category: String) {
    val circleColor = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f)

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
                .background(circleColor)
        )

        Text(text = category, modifier = Modifier.padding(start = 16.dp))

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { /* TODO: Edit category */ }) {
            Icon(Icons.Default.Edit, contentDescription = "Edit")
        }
        IconButton(onClick = { /* TODO: Delete category */ }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

////////////////////////////////



@Composable
fun GraphScreen() {
    var selectedView by remember { mutableStateOf("week") }

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
}

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
    }}

@Preview(showBackground = true)
@Composable
fun PreviewPeriodScreen() {
    GraphScreen()
}
