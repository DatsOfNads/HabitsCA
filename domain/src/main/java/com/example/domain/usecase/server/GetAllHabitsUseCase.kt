package com.example.domain.usecase.server

import com.example.domain.model.Habit
import com.example.domain.repository.ServerRepository
import javax.inject.Inject

class GetAllHabitsUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {

    suspend fun execute(): Pair<List<Habit>?, Int>{
        return serverRepository.getAllHabits()
    }
}