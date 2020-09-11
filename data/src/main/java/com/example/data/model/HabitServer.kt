package com.example.data.model

import java.io.Serializable

data class HabitServer(
    val uid: String,
    val title: String,
    val description: String,
    val priority: Int,
    val type: Int,
    val count: Int,
    val frequency: Int,
    val date: Long,
    val done_dates: List<String>
): Serializable