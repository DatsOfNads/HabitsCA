package com.example.domain.model

enum class Sort(val type: Int) {
    NONE(0),
    BY_PRIORITY(1),
    BY_NUMBER_OF_TIMES(2),
    BY_PERIOD(3)
}