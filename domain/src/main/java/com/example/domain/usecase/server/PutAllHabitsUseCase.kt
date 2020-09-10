package com.example.domain.usecase.server

import com.example.domain.model.Habit
import com.example.domain.repository.ServerRepository
import javax.inject.Inject

class PutAllHabitsUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {

    suspend fun execute(newHabits: List<Habit>, oldHabits: List<Habit>): Boolean{

        oldHabits.forEach {
            val response = serverRepository.deleteHabit(it.uid!!)

            if(response.second != 200)
                return false
        }

        newHabits.forEach {
            val response = serverRepository.putHabit(it)

            if(response.second != 200)
                return false
        }

        return true
    }
}