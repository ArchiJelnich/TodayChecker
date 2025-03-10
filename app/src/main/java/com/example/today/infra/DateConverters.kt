package com.example.today.infra

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun DateToString(date: LocalDate): String {
    val string_date = date.dayOfMonth.toString()+"."+date.monthValue.toString()+"."+date.year.toString()
    return string_date
}

@RequiresApi(Build.VERSION_CODES.O)
fun StrringToDate(date_string: String) : LocalDate
{
    val dateArrayList = date_string.split(".")
    val year = dateArrayList[2].toInt()
    val month = dateArrayList[1].toInt()
    val day = dateArrayList[0].toInt()
    val date = LocalDate.of(year, month, day)
    return date
}

