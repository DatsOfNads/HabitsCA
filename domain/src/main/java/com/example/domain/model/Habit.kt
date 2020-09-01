package com.example.domain.model

import java.io.Serializable

data class Habit(
    val title: String,
    val description: String,
    val priority: Int,
    val type: Int,
    val count: Int,
    val frequency: Int,
    val date: Long,
    var doneDates: List<String>
): Serializable