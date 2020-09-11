package com.example.domain.usecase.server

import com.example.domain.model.HabitDone
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

        newHabits.forEach {habit ->
            val responsePut = serverRepository.putHabit(habit)

            if(responsePut.second != 200)
                return false

            val uid = responsePut.first!!

            habit.doneDates?.forEach { doneDate ->
                val responsePost = serverRepository.postHabitDoneDate(HabitDone(doneDate.toLong(), uid))

                if(responsePost.second != 200)
                    return false
            }
        }

        return true
    }
}