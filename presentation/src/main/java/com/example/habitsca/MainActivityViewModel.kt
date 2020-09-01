package com.example.habitsca

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Habit
import com.example.domain.usecase.GetAllHabitsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val getAllHabitsUseCase: GetAllHabitsUseCase
): ViewModel() {

    private val allHabitsData = MutableLiveData<List<Habit>>()

    val subscribeAllHabits = allHabitsData as LiveData<List<Habit>>

    fun click(){
        viewModelScope.launch {
            val habits = getAllHabitsUseCase.execute()
            allHabitsData.value = habits
        }
    }
}