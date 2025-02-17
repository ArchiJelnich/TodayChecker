package com.example.today.compose

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.today.GraphActivity
import com.example.today.R
import com.example.today.SettingActivity
import com.example.today.TodayActivity


@Composable
fun BottomPanel() {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp)
            .background(Color.Gray)
            .padding(vertical  = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable (
                    onClick = {
                        context.startActivity(Intent(context, TodayActivity::class.java))
                    },)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.baseline_calendar_today_24),
                contentDescription = "Settings",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable (
                    onClick = {
                        context.startActivity(Intent(context, SettingActivity::class.java))
                    },)

        ){
            Image(
                painter = painterResource(id = R.drawable.baseline_settings_24),
                contentDescription = "Settings",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable (
                    onClick = {
                        context.startActivity(Intent(context, GraphActivity::class.java))
                    },)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.baseline_auto_graph_24),
                contentDescription = "Settings",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
