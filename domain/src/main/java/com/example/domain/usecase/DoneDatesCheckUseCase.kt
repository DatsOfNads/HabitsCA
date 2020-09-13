package com.example.domain.usecase

import com.example.domain.model.Habit
import com.example.domain.model.HabitState
import com.example.domain.model.`object`.Frequency.A_DAY
import com.example.domain.model.`object`.Frequency.A_MONTH
import com.example.domain.model.`object`.Frequency.A_WEEK
import com.example.domain.model.`object`.Frequency.A_YEAR
import com.example.domain.model.`object`.FrequencyCount.A_DAY_IN_MILLS
import com.example.domain.model.`object`.FrequencyCount.A_MONTH_IN_MILLS
import com.example.domain.model.`object`.FrequencyCount.A_WEEK_IN_MILLS
import com.example.domain.model.`object`.FrequencyCount.A_YEAR_IN_MILLS
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DoneDatesCheckUseCase @Inject constructor() {

    fun execute(habit: Habit): HabitState{
        val doneDates = habit.doneDates ?: listOf()//это ок?
        val timeStart = setTimeToStart(habit.date)
        val currentTime = setTimeToStart(doneDates.last().toLong())
        val frequencyInMills: Long = when(habit.frequency){
            A_DAY -> A_DAY_IN_MILLS
            A_WEEK -> A_WEEK_IN_MILLS
            A_MONTH -> A_MONTH_IN_MILLS
            A_YEAR -> A_YEAR_IN_MILLS
            else -> 0
        }

        var borderTime: Long = timeStart

        while (borderTime <= currentTime){
            borderTime += frequencyInMills
        }

        if(borderTime != currentTime){
            borderTime -= frequencyInMills
        }

        System.err.println("Точка отсчета ${printDate(borderTime)}")

        var doneCount = 0

        doneDates.forEach {
            if(it.toLong() > borderTime)
                doneCount++
        }

        return HabitState(habit.type, habit.count, doneCount)
    }

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("kk:mm", Locale.getDefault())

    private fun printDate(millis: Long): String{

        val dateFormat = dateFormat.format(millis)
        val timeFormat = timeFormat.format(millis)

        return  "Дата - $dateFormat, время $timeFormat"
    }

    private fun setTimeToStart(millis: Long): Long{
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        calendar.setTimeToStart()

        return calendar.timeInMillis
    }

    private fun Calendar.setTimeToStart(){
        this.set(Calendar.MILLISECOND, 0)
        this.set(Calendar.SECOND, 0)
        this.set(Calendar.MINUTE, 0)
        this.set(Calendar.HOUR_OF_DAY, 0)
    }
}