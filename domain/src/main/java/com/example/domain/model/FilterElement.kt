package com.example.domain.model

data class FilterElement(var title: String?, val type: Type, val sort: Sort){

    enum class Type(type: Int){
        SEARCH(0),
        SORT(1)
    }
}