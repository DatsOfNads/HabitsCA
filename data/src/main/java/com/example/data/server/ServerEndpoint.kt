package com.example.data.server

import com.example.data.response.ResponsePut
import com.example.domain.model.Habit
import retrofit2.Response
import retrofit2.http.*

interface ServerEndpoint {

    @GET("habit")
    suspend fun getHabits(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): Response<List<Habit>>

    @PUT("habit")
    suspend fun putHabit(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String,
        @Body habit: Habit
    ): Response<ResponsePut>

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String,
        @Body body: HashMap<String, String>
    ):Response<Unit>
}