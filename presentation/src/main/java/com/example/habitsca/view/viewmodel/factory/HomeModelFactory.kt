package com.example.habitsca.view.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.database.EditHabitUseCase
import com.example.domain.usecase.database.SubscribeAllDataUseCase
import com.example.habitsca.view.viewmodel.HomeFragmentModel
import javax.inject.Inject

class HomeModelFactory @Inject constructor(
    private val subscribeAllDataUseCase: SubscribeAllDataUseCase,
    private val editHabitUseCase: EditHabitUseCase
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeFragmentModel(subscribeAllDataUseCase, editHabitUseCase) as T
    }
}