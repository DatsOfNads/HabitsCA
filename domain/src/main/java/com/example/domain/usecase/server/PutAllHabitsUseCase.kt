package com.example.domain.usecase.server

import com.example.domain.model.HabitDone
import com.example.domain.model.Habit
import com.example.domain.repository.ServerRepository
import javax.inject.Inject

class PutAllHabitsUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {

    suspend fun execute(newHabits: List<Habit>, oldHabits: List<Habit>): Boolean{

        for(i in oldHabits.indices){
            val response = serverRepository.deleteHabit(oldHabits[i].uid!!)

            if(response.second != 200)
                return false
        }

        for(i in newHabits.indices){
            val responsePut = serverRepository.putHabit(newHabits[i])

            if(responsePut.second != 200)
                return false

            val uid = responsePut.first!!

            for(j in newHabits[i].doneDates?.indices!!){
                val responsePost = serverRepository.postHabitDoneDate(HabitDone(newHabits[i].doneDates!![j].toLong(), uid))

                if(responsePost.second != 200)
                    return false
            }
        }

        return true
    }
}