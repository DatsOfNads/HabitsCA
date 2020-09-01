package com.example.domain.repository

import com.example.domain.model.Habit

interface DatabaseRepository {
    suspend fun getAllData(): List<Habit>
    suspend fun addHabit(habit: Habit)
}