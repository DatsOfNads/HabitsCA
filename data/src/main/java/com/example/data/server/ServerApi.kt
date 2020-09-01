package com.example.data.server

import com.example.data.response.ResponsePut
import com.example.domain.model.Habit
import retrofit2.Response
import javax.inject.Inject

class ServerApi @Inject constructor(private val serverEndpoint: ServerEndpoint) {

    companion object{
        const val AUTH_KEY = "17390444-2928-4e9b-b91e-7cb8f886d78a"
        const val ACCEPT = "application/json"
        const val CONTENT_TYPE = "application/json"
    }

    suspend fun getHabits(): Response<List<Habit>>{
        return serverEndpoint.getHabits(ACCEPT, AUTH_KEY)
    }

    suspend fun putHabits(habit: Habit): Response<ResponsePut>{
        return  serverEndpoint.putHabit(ACCEPT, AUTH_KEY, CONTENT_TYPE, habit)
    }

    suspend fun deleteHabit(uid: String): Response<Unit>{
        val hashMap = HashMap<String, String>()
        hashMap["uid"] = uid

        return serverEndpoint.deleteHabit(ACCEPT, AUTH_KEY, CONTENT_TYPE, hashMap)
    }
}