package com.example.habitsca.view

import androidx.lifecycle.*
import com.example.domain.model.Habit
import com.example.domain.usecase.database.AddHabitUseCase
import com.example.domain.usecase.database.DeleteHabitUseCase
import com.example.domain.usecase.database.GetAllDataUseCase
import com.example.domain.usecase.database.SubscribeAllDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val addHabitUseCase: AddHabitUseCase,
    private val getAllDataUseCase: GetAllDataUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    subscribeAllDataUseCase: SubscribeAllDataUseCase
): ViewModel() {

    val subscribeAllHabits = subscribeAllDataUseCase.execute().asLiveData()

    fun putHabitToDataBase(habit: Habit){
        viewModelScope.launch {
            addHabitUseCase.execute(habit)
        }
    }

    fun getHabitsFromDatabase(){
        viewModelScope.launch {
            val habits = getAllDataUseCase.execute()
            System.err.println("habits $habits")
        }
    }

    fun deleteHabit(habit: Habit){
        viewModelScope.launch {
            deleteHabitUseCase.execute(habit)
        }
    }
}