package com.example.data.response

import java.io.Serializable

data class HabitsResponse(
    val uid: String,
    val title: String,
    val description: String,
    val priority: Int,
    val type: Int,
    val count: Int,
    val frequency: Int,
    val date: Long,
    var doneDates: List<String>?
): Serializable