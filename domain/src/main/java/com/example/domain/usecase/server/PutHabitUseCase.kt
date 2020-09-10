package com.example.domain.usecase.server

import com.example.domain.model.Habit
import com.example.domain.repository.ServerRepository
import javax.inject.Inject

class PutHabitUseCase @Inject constructor(private val serverRepository: ServerRepository) {

    suspend fun execute(habit: Habit): Pair<String?, Int>{
        return serverRepository.putHabit(habit)
    }
}