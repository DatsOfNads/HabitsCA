package com.example.habitsca.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.LoadingState
import com.example.domain.usecase.database.GetAllDataUseCase
import com.example.domain.usecase.database.UpdateAllDataUseCase
import com.example.domain.usecase.server.GetAllHabitsUseCase
import com.example.domain.usecase.server.PutAllHabitsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SyncFragmentModel @Inject constructor(
    //Для выгрузки
    private val getAllFromDatabase: GetAllDataUseCase,
    private val putAllHabitsUseCase: PutAllHabitsUseCase,
    //Для загрузки
    private val getAllFromServer: GetAllHabitsUseCase,
    private val updateAllDataUseCase: UpdateAllDataUseCase
): ViewModel() {

    private val loadingStateData = MutableLiveData<LoadingState>()
    fun subscribeLoadingState() = loadingStateData as LiveData<LoadingState>

    init {
        loadingStateData.value = LoadingState.START
    }

    fun getAllData(){
        viewModelScope.launch {
            loadingStateData.value = LoadingState.LOADING

            val response = getAllFromServer.execute()

            if(response.second == 200){
                updateAllDataUseCase.execute(response.first!!)
                loadingStateData.value = LoadingState.DONE
            } else{
                loadingStateData.value = LoadingState.ERROR
            }
        }
    }

    fun setAllData(){
        viewModelScope.launch {
            loadingStateData.value = LoadingState.LOADING

            val response = getAllFromServer.execute()

            System.err.println("делаем пут вот че ${response.second}")

            if(response.second == 200){
                val newHabits = getAllFromDatabase.execute()
                val isSuccessful = putAllHabitsUseCase.execute(newHabits, response.first!!)

                if(isSuccessful){
                    loadingStateData.value = LoadingState.DONE
                } else{
                    loadingStateData.value = LoadingState.ERROR
                }
            }else{
                loadingStateData.value = LoadingState.ERROR
            }

        }
    }
}