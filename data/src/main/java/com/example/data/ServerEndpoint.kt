package com.example.data

import com.example.data.response.HabitsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ServerEndpoint {

    @GET("habit")
    suspend fun getHabits(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): Response<List<HabitsResponse>>
}