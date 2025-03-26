package com.example.today.infra

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun dateToString(date: LocalDate): String {
    val stringDate = date.dayOfMonth.toString()+"."+date.monthValue.toString()+"."+date.year.toString()
    return stringDate
}

@RequiresApi(Build.VERSION_CODES.O)
fun stringToDate(dateString: String) : LocalDate
{
    val dateArrayList = dateString.split(".")

    Log.d("MyDebug", "dateArrayList " + dateArrayList.toString())

    val year = dateArrayList[2].toInt()
    val month = dateArrayList[1].toInt()
    val day = dateArrayList[0].toInt()
    val date = LocalDate.of(year, month, day)
    return date
}

