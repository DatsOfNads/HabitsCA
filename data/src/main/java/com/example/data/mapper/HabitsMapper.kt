package com.example.data.mapper

import com.example.data.model.HabitRoom
import com.example.domain.model.Habit
import javax.inject.Inject

class HabitsMapper @Inject constructor() {

    //ГОВНО ГОВНО ПЕРЕИМЕНУЙ
    fun mapServerHabitToHabit(it: Habit): Habit{

        val doneDates = it.doneDates ?: listOf()

        return Habit(
                uid = it.uid,
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

    //ГОВНО ГОВНО ПЕРЕИМЕНУЙ ААААААААААААААА
    fun mapHabitsRoomToHabit(it: HabitRoom): Habit{
        return Habit(
            uid = it.uid,
            title = it.title,
            description = it.description,
            priority = it.priority,
            count = it.count,
            type = it.type,
            frequency = it.frequency,
            date = it.date,
            doneDates = it.doneDates
        )
    }
}