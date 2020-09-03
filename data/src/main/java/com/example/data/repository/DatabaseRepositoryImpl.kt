package com.example.data.repository

import com.example.data.database.HabitsDao
import com.example.data.mapper.HabitsMapper
import com.example.domain.model.Habit
import com.example.domain.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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

    override fun subscribeAllData(): Flow<List<Habit>> {

        return habitsDao.subscribeAllData().map {
            it.map { habitRoom ->
                habitsMapper.mapHabitsRoomToHabit(habitRoom)
            }
        }.flowOn(Dispatchers.Default)
            .conflate()
    }

    override suspend fun addHabit(habit: Habit) {
        val habitRoom = habitsMapper.mapHabitToRoomHabit(habit)
        habitsDao.addHabit(habitRoom)
    }

    override suspend fun editHabit(habit: Habit) {
        val habitRoom = habitsMapper.mapHabitToRoomHabit(habit)
        habitsDao.editHabit(habitRoom)
    }

    override suspend fun deleteHabit(habit: Habit) {
        val habitRoom = habitsMapper.mapHabitToRoomHabit(habit)
        habitsDao.deleteHabit(habitRoom)
    }

    override suspend fun deleteAll() {
        habitsDao.deleteAll()
    }
}