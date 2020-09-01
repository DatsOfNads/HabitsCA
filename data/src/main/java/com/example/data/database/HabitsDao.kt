package com.example.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.data.model.HabitRoom

@Dao
interface HabitsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHabit(habit: HabitRoom)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun editHabit(habit: HabitRoom)

    @Delete
    suspend fun deleteHabit(habit: HabitRoom)

    @Query("DELETE FROM habits_data")
    suspend fun deleteAll()

    @Query("SELECT * FROM habits_data ORDER BY date DESC")
    fun subscribeAllData(): LiveData<List<HabitRoom>>

    @Query("SELECT * FROM habits_data ORDER BY date DESC")
    fun getAllData(): List<HabitRoom>
}