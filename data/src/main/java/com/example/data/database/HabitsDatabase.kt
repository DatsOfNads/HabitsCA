package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.model.HabitRoom

@Database(entities = [HabitRoom::class], version = 1, exportSchema = false)
@TypeConverters(HabitRoom.HabitsConverter::class)
abstract class HabitsDatabase: RoomDatabase() {
    abstract fun habitsDao(): HabitsDao
}