package com.example.domain.usecase.database

import com.example.domain.model.Habit
import com.example.domain.repository.DatabaseRepository
import javax.inject.Inject

class UpdateAllDataUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {

    suspend fun execute(habits: List<Habit>){
        databaseRepository.deleteAll()
        habits.forEach {
            databaseRepository.addHabit(it)
        }
    }
}