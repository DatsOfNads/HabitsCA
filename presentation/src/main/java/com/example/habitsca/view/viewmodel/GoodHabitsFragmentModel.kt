package com.example.habitsca.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.domain.model.Habit
import com.example.domain.usecase.database.SubscribeAllDataUseCase
import javax.inject.Inject

class GoodHabitsFragmentModel @Inject constructor(
    subscribeAllDataUseCase: SubscribeAllDataUseCase
): ViewModel() {
    val subscribeAllData: LiveData<List<Habit>> = subscribeAllDataUseCase.execute().asLiveData()
}