package com.example.habitsca.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Habit
import com.example.domain.usecase.database.AddHabitUseCase
import kotlinx.coroutines.launch

import javax.inject.Inject

class AddFragmentModel @Inject constructor(
    private val addHabitUseCase: AddHabitUseCase
): ViewModel() {

    fun addHabit(habit: Habit){
        viewModelScope.launch {
            addHabitUseCase.execute(habit)
        }
    }
}