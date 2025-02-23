package com.example.today.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category")
    fun getAll():  List<Category>
    @Insert
    fun insertAll(vararg category: Category)
    @Query("SELECT * FROM Category WHERE deletedFlag = 0")
    suspend fun getNotDeleted():  List<Category>
    @Query("DELETE FROM Category")
    fun deleteAll()
}

@Dao
interface DateInfoDao {
    @Query("SELECT * FROM DateInfo")
    fun getAllDate(): List<DateInfo>
}
