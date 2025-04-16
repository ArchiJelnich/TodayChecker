package com.example.today.compose

import android.content.Intent
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
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
import com.example.today.CategoryViewModel
import com.example.today.R
import com.example.today.TodayActivity
import com.example.today.infra.flagPut
import com.example.today.infra.loadTheme
import com.example.today.infra.parseMapFromString
import com.example.today.infra.stringToDate
import com.example.today.infra.toColor
import com.example.today.infra.updateMap

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayScreen(viewModel: CategoryViewModel, dateInfo : String, dateAsset: Int, dateText : String) {

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
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {


        var asset = dateAsset
        var firstDayCheck = 0

        // StringToDate(dateText)

        // Log.d("MyDebug", "StringToDate(dateText)" + StringToDate(dateText).toString())

        if (asset!=0 && stringToDate(dateText).dayOfMonth==1)
        {
            firstDayCheck = 1
        }





        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Absolute.Center) {
            if (firstDayCheck == 0){
                Button(onClick = {
                    asset--
                    val intent = Intent(context, TodayActivity::class.java)
                    intent.putExtra("asset", asset)
                    context.startActivity(intent)
                }) { Text("<") }
            }
            Text(
                text = dateText,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .padding(bottom = 40.dp),
                textAlign = TextAlign.Center
            )
            if (asset != 0){
                Button(onClick = {
                    asset++
                    val intent = Intent(context, TodayActivity::class.java)
                    intent.putExtra("asset", asset)
                    context.startActivity(intent)
                }) { Text(">") }
            }
        }

        //val categories_old = listOf("Work", "Study", "Exercise", "Relax", "Hobby", "1", "2", "3", "4", "123", "sdas", "as") // TEMPORARY ZAGLUSHKA
        val categories by viewModel.categories.collectAsState(initial = emptyList())

        var countsMap = remember { mutableStateMapOf<Int, Int>() }
        val totalCount by remember { derivedStateOf { countsMap.values.sum() } }
        var showLimitDialog by remember { mutableStateOf(false) }


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
                            dateInfo = dateInfo,
                            countsMap = countsMap,
                            totalCount = totalCount,
                            showLimitDialog = { showLimitDialog = true }
                        )
                    }
                }
            }

        }


        if (showLimitDialog) {
            AlertDialog(
                onDismissRequest = { showLimitDialog = false },
                confirmButton = {
                    TextButton(onClick = { showLimitDialog = false }) {
                        Text("OK")
                    }
                },
                title = { Text(stringResource(R.string.str_limit)) }
            )
        }

        BottomPanel()
    }


}}}


@Composable
fun CategoryItem(cName: String, cColor: String, viewModel: CategoryViewModel, cID: Int, dateInfo: String, countsMap: MutableMap<Int, Int>, totalCount: Int, showLimitDialog: () -> Unit) {

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
    val circleColor = cColor.toColor()
    val context = LocalContext.current
    //val circleColor = Color(cColor.toLong())

    LaunchedEffect(count) {
        countsMap[cID] = count
    }

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
            Button(
                onClick = {
                    if (totalCount < 24) {
                        count++
                        flagPut(context, 1)
                        updateMap(context, cID, count)
                    } else {
                        showLimitDialog()
                    }
                },

                enabled = totalCount <= 24
            ) {
                Text("+")
            }


        }
    }

}