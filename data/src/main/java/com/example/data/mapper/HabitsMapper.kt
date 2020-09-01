package com.example.data.mapper

import com.example.data.response.HabitsResponse
import com.example.domain.model.Habit
import javax.inject.Inject

class HabitsMapper @Inject constructor() {
    fun map(it: HabitsResponse): Habit{

        val doneDates = it.doneDates ?: listOf()

        return Habit(
                title = it.title,
                description = it.description,
                priority = it.priority,
                count = it.count,
                type = it.type,
                frequency = it.frequency,
                date = it.date,
                doneDates = doneDates
            )
    }
}