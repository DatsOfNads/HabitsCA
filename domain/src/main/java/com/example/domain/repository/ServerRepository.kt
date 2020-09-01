package com.example.domain.repository

import com.example.domain.model.Habit

interface ServerRepository {
    suspend fun getAllHabits(): List<Habit>?
    suspend fun putHabit(habit: Habit): String?
    suspend fun deleteHabit(uid: String): Unit?
}