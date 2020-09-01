package com.example.data.repository

import com.example.data.database.HabitsDao
import com.example.data.mapper.HabitsMapper
import com.example.data.model.HabitRoom
import com.example.domain.model.Habit
import com.example.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val habitsDao: HabitsDao,
    private val habitsMapper: HabitsMapper
): DatabaseRepository{

    override suspend fun getAllData(): List<Habit> {
        val habits =  habitsDao.getAllData()

        return habits.map {
            habitsMapper.mapHabitsRoomToHabit(it)
        }
    }

    override suspend fun addHabit(habit: Habit) {

        //КОСТЫЫЫЫЛЬЬЬЬЬ ПЕРЕНИСИ В МАППЕР
        val habitRoom = HabitRoom(
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

        habitsDao.addHabit(habitRoom)
    }
}