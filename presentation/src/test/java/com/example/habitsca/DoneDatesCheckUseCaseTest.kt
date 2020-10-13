package com.example.habitsca

import com.example.domain.model.Habit
import com.example.domain.model.`object`.Frequency
import com.example.domain.model.`object`.Priority
import com.example.domain.model.`object`.Type
import com.example.domain.usecase.DoneDatesCheckUseCase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class DoneDatesCheckUseCaseTest {

    companion object{
        const val START_DATE: Long = 1596650400000// 5 августа 2020, понедельник, 6 часов вечера

        //----------------Первая неделя------------------------------------------------
        const val DATE_1: Long = 1596643200000// 5 августа 2020, понедельник, 4 часа утра
        const val DATE_2: Long = 1597046400000// 10 августа 2020, суббота, 8 часов утра
        const val DATE_3: Long = 1597136400000// 11 августа 2020, воскресенье, 9 часов вечера

        //----------------Вторая неделя------------------------------------------------
        const val DATE_4: Long = 1597233600000// 12 августа 2020, понедельник, полдень
        const val DATE_5: Long = 1597284000000// 13 августа 2020, вторник, 2 часа ночи
        const val DATE_6: Long = 1597438800000// 14 августа 2020, среда, 9 часов вечера
    }

    @Before
    fun before(){
        MockitoAnnotations.openMocks(this)
        doneDatesCheckUseCase = DoneDatesCheckUseCase()
    }

    @Mock
    lateinit var doneDatesCheckUseCase: DoneDatesCheckUseCase

    private val doneDates = mutableListOf<String>()

    private val habit = Habit(
        "some uid",
        "some title",
        "some description",
        Priority.LOW,
        Type.GOOD,
        2,
        Frequency.A_WEEK,
        START_DATE,
        listOf()
    )

    @Test
    fun doneDatesCheckUseTest(){

        doneDates.add(DATE_1.toString())
        habit.doneDates = doneDates
        Assert.assertEquals(doneDatesCheckUseCase.execute(habit).doneCount, 1)

        doneDates.add(DATE_2.toString())
        habit.doneDates = doneDates
        Assert.assertEquals(doneDatesCheckUseCase.execute(habit).doneCount, 2)

        doneDates.add(DATE_3.toString())
        habit.doneDates = doneDates
        Assert.assertEquals(doneDatesCheckUseCase.execute(habit).doneCount, 3)

        doneDates.add(DATE_4.toString())
        habit.doneDates = doneDates
        Assert.assertEquals(doneDatesCheckUseCase.execute(habit).doneCount, 1)

        doneDates.add(DATE_5.toString())
        habit.doneDates = doneDates
        Assert.assertEquals(doneDatesCheckUseCase.execute(habit).doneCount, 2)

        doneDates.add(DATE_6.toString())
        habit.doneDates = doneDates
        Assert.assertEquals(doneDatesCheckUseCase.execute(habit).doneCount, 3)
    }
}