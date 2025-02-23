package com.example.today.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class Category(
    @PrimaryKey(autoGenerate = true) var cID: Int,
    @ColumnInfo(name = "cName") val cName: String?,
    @ColumnInfo(name = "cColor") val cColor: Long?,
    @ColumnInfo(name = "deletedFlag") val deletedFlag: Boolean?,
)

@Entity(tableName = "DateInfo")
data class DateInfo(
    @PrimaryKey(autoGenerate = true) var dID: Int,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "date_info") val date_info: String?,
)