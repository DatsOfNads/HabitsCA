package com.example.data.mapper

import com.example.data.model.HabitRoom
import com.example.domain.model.Habit
import javax.inject.Inject

class HabitsMapper @Inject constructor() {

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

    fun mapHabitToRoomHabit(habit: Habit): HabitRoom{
        return HabitRoom(
            uid = habit.uid!!,
            title = habit.title,
            description = habit.description,
            priority = habit.priority,
            type = habit.type,
            count = habit.count,
            frequency = habit.frequency,
            date = habit.date,
            doneDates = habit.doneDates
        )
    }
}