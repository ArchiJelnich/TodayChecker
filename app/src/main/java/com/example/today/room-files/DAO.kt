package com.example.today.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

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
    @Update
    fun updateCategory(category: Category)
    @Query("UPDATE Category SET deletedFlag = 1 WHERE cID=:cID")
    fun setDelete(cID: Int)
    @Query("SELECT * FROM Category WHERE deletedFlag = 0")
    fun getNotDeletedFlow(): Flow<List<Category>>

}

@Dao
interface DateInfoDao {
    @Query("SELECT * FROM DateInfo")
    fun getAllDate(): List<DateInfo>
    @Query("SELECT date_info FROM DateInfo WHERE date=:date")
    fun getDateInfoByDay(date: String): String
    @Insert
    fun insert(vararg dateInfo: DateInfo)
    @Query("UPDATE dateInfo SET date_info = :date_info WHERE date =:date")
    fun update(date_info: String, date: String)
    @Query("SELECT dID FROM DateInfo WHERE date=:date")
    fun getIDByDay(date: String): Int
    @Query("SELECT * FROM DateInfo WHERE year=:year")
    fun getIDByYear(year: Int): List<DateInfo>
    @Query("SELECT * FROM DateInfo WHERE month=:month")
    fun getIDByMonth(month: Int): List<DateInfo>
    @Query("SELECT * FROM DateInfo WHERE date=:date")
    fun getIDByDate(date: String): DateInfo
    @Query("DELETE FROM DateInfo")
    fun deleteAll()
}

@Dao
interface AverageDao {
    @Query("SELECT * FROM AverageInfo")
    fun getAllAverage(): List<AverageInfo>
    @Query("DELETE FROM AverageInfo")
    fun deleteAll()
}