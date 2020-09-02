package com.example.domain.repository

import com.example.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun addHabit(habit: Habit)
    suspend fun editHabit(habit: Habit)
    suspend fun deleteHabit(habit: Habit)
    suspend fun deleteAll()
    suspend fun getAllData(): List<Habit>
    fun subscribeAllData(): Flow<List<Habit>>
}