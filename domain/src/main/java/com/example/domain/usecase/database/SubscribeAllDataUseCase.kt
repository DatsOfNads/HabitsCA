package com.example.domain.usecase.database

import com.example.domain.model.Habit
import com.example.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeAllDataUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {

    fun execute(): Flow<List<Habit>>{
        return databaseRepository.subscribeAllData()
    }
}