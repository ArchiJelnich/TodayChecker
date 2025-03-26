package com.example.today.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Category::class, DateInfo::class, AverageInfo::class],version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CategoryDao(): CategoryDao
    abstract fun DateInfoDao(): DateInfoDao
    abstract fun AverageDao(): AverageDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS AverageInfo (
                aID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                type TEXT, 
                month INTEGER, 
                year INTEGER, 
                averageInfo TEXT
            )
            """.trimIndent()
                )


            }
        }



        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "today_app_database"
                ).addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }


    }


}

