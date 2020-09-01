package com.example.domain.usecase.server

import com.example.domain.repository.ServerRepository
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(private val serverRepository: ServerRepository) {

    suspend fun execute(uid: String): Unit?{
        return serverRepository.deleteHabit(uid)
    }
}