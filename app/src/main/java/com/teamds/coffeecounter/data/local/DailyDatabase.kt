package com.teamds.coffeecounter.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.teamds.coffeecounter.data.local.dao.DailyDao
import com.teamds.coffeecounter.model.dailydb.Converters
import com.teamds.coffeecounter.model.dailydb.DailyData


@Database(entities = arrayOf(DailyData::class), version = 1)
@TypeConverters(Converters::class)
    abstract class DailyDatabase : RoomDatabase() {
        abstract fun dailyDao(): DailyDao

    companion object {

        private var instance: DailyDatabase? = null

        @Synchronized
        fun getInstance(context: Context): DailyDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    DailyDatabase::class.java, "daily_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}