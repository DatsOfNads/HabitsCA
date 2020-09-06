package com.example.domain.model

enum class LoadingState(state: Int){
    START(0),
    LOADING(1),
    DONE(2),
    ERROR(3)
}