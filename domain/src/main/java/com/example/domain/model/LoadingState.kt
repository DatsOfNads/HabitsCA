package com.example.domain.model

enum class LoadingState(state: Int){
    START(0),
    LOADING(1),
    DONE(2),
    SERVER_ERROR(3),
    CONNECTION_ERROR(4)
}