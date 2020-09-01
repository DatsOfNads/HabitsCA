package com.example.habitsca.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Habit
import com.example.domain.usecase.DeleteHabitUseCase
import com.example.domain.usecase.GetAllHabitsUseCase
import com.example.domain.usecase.PutHabitUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val getAllHabitsUseCase: GetAllHabitsUseCase,
    private val putHabitUseCase: PutHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase
): ViewModel() {

    private val allHabitsData = MutableLiveData<List<Habit>>()

    val subscribeAllHabits = allHabitsData as LiveData<List<Habit>>

    fun getAllHabits(){
        viewModelScope.launch {
            val habits = getAllHabitsUseCase.execute()
            allHabitsData.value = habits
        }
    }

    fun putHabit(habit: Habit){
        viewModelScope.launch {
            val uid = putHabitUseCase.execute(habit)
            System.err.println("Пушнули $uid")
        }
    }

    fun deleteHabit(uid: String){
        viewModelScope.launch {
            val unit = deleteHabitUseCase.execute(uid)
            System.err.println("Удолил $unit")
        }
    }
}