package com.example.domain.repository

import com.example.domain.model.HabitDone
import com.example.domain.model.Habit

interface ServerRepository {
    suspend fun getAllHabits(): Pair<List<Habit>?, Int>
    suspend fun putHabit(habit: Habit): Pair<String?, Int>
    suspend fun postHabitDoneDate(habitDone: HabitDone): Pair<Unit?, Int>
    suspend fun deleteHabit(uid: String): Pair<Unit?, Int>
}