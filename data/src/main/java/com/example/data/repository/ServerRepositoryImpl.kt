package com.example.data.repository

import com.example.data.server.ServerApi
import com.example.data.mapper.HabitsMapper
import com.example.domain.model.HabitDone
import com.example.domain.model.Habit
import com.example.domain.repository.ServerRepository

class ServerRepositoryImpl(
    private val serverApi: ServerApi,
    private val habitsMapper: HabitsMapper
) : ServerRepository {

    override suspend fun getAllHabits(): Pair<List<Habit>?, Int> {

        val response = serverApi.getHabits()

        if(response.isSuccessful){
            val mappedHabits =  response.body()?.map {
                habitsMapper.mapServerHabitToHabit(it)
            }

            return Pair(mappedHabits, response.code())
        }

        return Pair(null, response.code())
    }

    override suspend fun putHabit(habit: Habit): Pair<String?, Int> {
        habit.uid = null

        val response = serverApi.putHabits(habit)

        if (response.isSuccessful){
            val uid = response.body()?.uid
            return Pair(uid, response.code())
        }

        return Pair(null, response.code())
    }

    override suspend fun postHabitDoneDate(habitDone: HabitDone): Pair<Unit?, Int> {
        val response = serverApi.postHabitDoneDate(habitDone)

        if(response.isSuccessful){
            return Pair(response.body(), response.code())
        }

        return Pair(null, response.code())
    }

    override suspend fun deleteHabit(uid: String): Pair<Unit?, Int> {
        val hashMap = HashMap<String, String>()
        hashMap["uid"] = uid

        val response = serverApi.deleteHabit(uid)

        if (response.isSuccessful){
            return Pair(response.body(), response.code())
        }

        return Pair(null, response.code())
    }
}