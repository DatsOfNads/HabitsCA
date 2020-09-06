package com.example.habitsca.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Habit
import com.example.domain.usecase.database.DeleteHabitUseCase
import com.example.domain.usecase.database.EditHabitUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditFragmentModel @Inject constructor(
    private val editHabitUseCase: EditHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase
): ViewModel() {

    fun editHabit(habit: Habit){
        viewModelScope.launch {
            editHabitUseCase.execute(habit)
        }
    }

    fun deleteHabit(habit: Habit){
        viewModelScope.launch {
            deleteHabitUseCase.execute(habit)
        }
    }
}