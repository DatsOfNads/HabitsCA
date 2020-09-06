package com.example.domain.usecase.server

import com.example.domain.model.Habit
import com.example.domain.repository.ServerRepository
import javax.inject.Inject

class PutAllHabitsUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {

    suspend fun execute(newHabits: List<Habit>, oldHabits: List<Habit>): Boolean{

        oldHabits.forEach {
            serverRepository.deleteHabit(it.uid!!) ?: return false
        }

        newHabits.forEach {
            serverRepository.putHabit(it)  ?: return false
        }

        return true
    }
}