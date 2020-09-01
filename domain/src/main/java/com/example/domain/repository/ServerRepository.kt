package com.example.domain.repository

import com.example.domain.model.Habit

interface ServerRepository {
    suspend fun getAllHabits(): List<Habit>?
}