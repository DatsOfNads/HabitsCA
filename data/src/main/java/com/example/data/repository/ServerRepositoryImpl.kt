package com.example.data.repository

import com.example.data.ServerApi
import com.example.data.mapper.HabitsMapper
import com.example.domain.model.Habit
import com.example.domain.repository.ServerRepository

class ServerRepositoryImpl(
    private val serverApi: ServerApi,
    private val habitsMapper: HabitsMapper
) : ServerRepository {

    override suspend fun getAllHabits(): List<Habit>? {

        val response = serverApi.getHabits()

        return if (response.isSuccessful)
            response.body()?.map {
                habitsMapper.map(it)
            }
        else
            null
    }
}