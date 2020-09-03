package com.example.habitsca.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Habit
import com.example.domain.usecase.database.EditHabitUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditFragmentModel @Inject constructor(
    private val editHabitUseCase: EditHabitUseCase
): ViewModel() {

    fun editHabit(habit: Habit){
        viewModelScope.launch {
            editHabitUseCase.execute(habit)
        }
    }
}