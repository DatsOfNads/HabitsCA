package com.example.domain.model

data class FilterElement(val type: Type){

    enum class Type(type: Int){
        SEARCH(0),
        SORT(1)
    }
}