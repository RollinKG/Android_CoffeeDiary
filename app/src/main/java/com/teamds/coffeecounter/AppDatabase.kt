package com.teamds.coffeecounter

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(CoffeeData::class), version = 1)
@TypeConverters(Converters::class)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun coffeeDao(): CoffeeDao
    }