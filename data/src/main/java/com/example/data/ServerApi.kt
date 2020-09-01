package com.example.data

import com.example.data.response.HabitsResponse
import retrofit2.Response
import javax.inject.Inject

class ServerApi @Inject constructor(private val serverEndpoint: ServerEndpoint) {

    companion object{
        const val AUTH_KEY = "17390444-2928-4e9b-b91e-7cb8f886d78a"
        const val ACCEPT = "application/json"
        const val CONTENT_TYPE = "application/json"
    }

    suspend fun getHabits(): Response<List<HabitsResponse>>{
        return serverEndpoint.getHabits(ACCEPT, AUTH_KEY)
    }
}