package com.example.today.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Category")
data class Category (
    @PrimaryKey(autoGenerate = true) var cID: Int,
    @ColumnInfo(name = "cName") val cName: String?,
    @ColumnInfo(name = "cColor") val cColor: String?,
    @ColumnInfo(name = "deletedFlag") val deletedFlag: Boolean?,
) : Serializable

@Entity(tableName = "DateInfo")
data class DateInfo(
    @PrimaryKey(autoGenerate = true) var dID: Int,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "date_info") val date_info: String?,
    @ColumnInfo(name = "day") val day: String?,
    @ColumnInfo(name = "month") val month: String?,
    @ColumnInfo(name = "year") val year: String?,
)

@Entity(tableName = "AverageInfo")
data class AverageInfo(
    @PrimaryKey(autoGenerate = true) var aID: Int,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "month") val month: Int?,
    @ColumnInfo(name = "year") val year: Int?,
    @ColumnInfo(name = "averageInfo") val averageInfo: String?,
)