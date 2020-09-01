package com.example.domain.usecase.database

import com.example.domain.model.Habit
import com.example.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddHabitUseCase @Inject constructor(private val databaseRepository: DatabaseRepository) {

    suspend fun execute(habit: Habit){
        return databaseRepository.addHabit(habit)
    }
}