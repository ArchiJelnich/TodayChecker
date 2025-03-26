package com.example.today.infra

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.today.room.DateInfo
import com.example.today.room.DateInfoDao
import java.sql.Date
import java.time.LocalDate
import java.time.YearMonth

fun AverageByMonths(dateInfoDao : DateInfoDao, year : Int): MutableMap<Int, Double> {

    val listOfInfo = dateInfoDao.getIDByYear(year)
    var finalInfo = mutableMapOf<Int, Double>()

    if (listOfInfo.size!=0) {
        for (info in listOfInfo) {


            val infoPerMonth = info.date_info?.let { parseMapFromStringDouble(it) }
            if (infoPerMonth != null) {
                finalInfo = (infoPerMonth.keys + finalInfo.keys).associateWith { key ->
                    (infoPerMonth[key] ?: 0.0) + (finalInfo[key] ?: 0.0)
                } as MutableMap<Int, Double>
            }

        }
    }

    finalInfo = finalInfo.mapValues { it.value / 12.0 } as MutableMap<Int, Double>

    return finalInfo

}


@RequiresApi(Build.VERSION_CODES.O)
fun AverageForMonths(dateInfoDao : DateInfoDao, month : Int, year: Int): MutableMap<Int, Double> {

    val listOfInfo = dateInfoDao.getIDByMonth(month)
    //Log.d("MyDebug", "Average: " + dateInfoDao.getAllDate().toString())
    var finalInfo = mutableMapOf<Int, Double>()

    if (listOfInfo.size!=0) {
        for (info in listOfInfo) {


            val infoForMonth = info.date_info?.let { parseMapFromStringDouble(it) }
            if (infoForMonth != null) {
                finalInfo = (infoForMonth.keys + finalInfo.keys).associateWith { key ->
                    (infoForMonth[key] ?: 0.0) + (finalInfo[key] ?: 0.0)
                } as MutableMap<Int, Double>
            }

        }
    }


    val count = YearMonth.of(year, month).lengthOfMonth().toDouble()

    finalInfo = finalInfo.mapValues {
        val result = it.value/ count
        result.toDouble()
    } as MutableMap<Int, Double>


    return finalInfo

}



@RequiresApi(Build.VERSION_CODES.O)
fun AverageForWeek(dateInfoDao : DateInfoDao, date: LocalDate): MutableMap<Int, Double> {

    var listOfInfo = ArrayList<DateInfo>()
    var finalInfo = mutableMapOf<Int, Double>()
    var dateOfWeek = date.dayOfWeek.value.toInt()
    for (i in 1 until dateOfWeek)
    {
        listOfInfo.add(dateInfoDao.getIDByDate(dateToString(date)))
    }

    if (listOfInfo.size!=0) {
        for (info in listOfInfo) {

            val infoForWeek = info.date_info?.let { parseMapFromStringDouble(it) }
            if (infoForWeek != null) {
                finalInfo = (infoForWeek.keys + finalInfo.keys).associateWith { key ->
                    (infoForWeek[key] ?: 0.0) + (finalInfo[key] ?: 0.0)
                } as MutableMap<Int, Double>
            }

        }
    }


    Log.d("MyDebug", "Average: " + finalInfo.toString())


    finalInfo = finalInfo.mapValues {
        val result = it.value/7
        result.toDouble()
    } as MutableMap<Int, Double>

    Log.d("MyDebug", "Average: " + finalInfo.toString())

    return finalInfo

}