package com.example.data.repository

import com.example.data.server.ServerApi
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

    override suspend fun putHabit(habit: Habit): String? {
        habit.uid = null

        val response = serverApi.putHabits(habit)

        return if (response.isSuccessful)
            response.body()?.uid
        else
            null
    }

    override suspend fun deleteHabit(uid: String): Unit? {
        val hashMap = HashMap<String, String>()
        hashMap["uid"] = uid

        val response = serverApi.deleteHabit(uid)

        return if (response.isSuccessful)
            response.body()
        else
            null
    }
}