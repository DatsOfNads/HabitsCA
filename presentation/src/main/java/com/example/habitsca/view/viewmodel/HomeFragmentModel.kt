package com.example.habitsca.view.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.domain.model.FilterElement
import com.example.domain.model.Habit
import com.example.domain.model.HabitState
import com.example.domain.model.Sort
import com.example.domain.model.`object`.Event
import com.example.domain.model.`object`.Frequency
import com.example.domain.model.`object`.Type
import com.example.domain.usecase.DoneDatesCheckUseCase
import com.example.domain.usecase.database.EditHabitUseCase
import com.example.domain.usecase.database.SubscribeAllDataUseCase
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class HomeFragmentModel constructor(
    subscribeAllDataUseCase: SubscribeAllDataUseCase,
    private val editHabitUseCase: EditHabitUseCase,
    private val doneDatesCheckUseCase: DoneDatesCheckUseCase
) : ViewModel() {

    private var habits: ArrayList<Habit> = arrayListOf()
    private var goodHabits: ArrayList<Habit> = arrayListOf()
    private var badHabits: ArrayList<Habit> = arrayListOf()

    private val goodHabitsData = MutableLiveData<List<Habit>>()
    private val badHabitsData = MutableLiveData<List<Habit>>()
    private val sortDirectionData = MutableLiveData<Boolean>()
    private val filterElementData = MutableLiveData<ArrayList<FilterElement>>()
    private val habitStateData = MutableLiveData<Event<HabitState>>()

    private var sortDirection = true
    private var sortType = Sort.NONE
    private var searchedText = ""
    private val filterElements = arrayListOf<FilterElement>()

    fun subscribeGoodHabits() = goodHabitsData as LiveData<List<Habit>>
    fun subscribeBadHabits() = badHabitsData as LiveData<List<Habit>>
    fun subscribeSortDirection() = sortDirectionData as LiveData<Boolean>
    fun subscribeFilterElement() = filterElementData as LiveData<ArrayList<FilterElement>>
    fun subscribeHabitState() = habitStateData as LiveData<Event<HabitState>>

    private val observer = Observer<List<Habit>> {
        habits = checkSortDirection(it as ArrayList<Habit>)

        val (goodHabits, badHabits) = splitHabits(habits)

        this.goodHabits = goodHabits as ArrayList<Habit>
        this.badHabits = badHabits as ArrayList<Habit>

        sortHabits(sortType)
    }

    init {
        sortDirectionData.value = true
        subscribeAllDataUseCase.execute().asLiveData().observeForever(observer)
    }

    fun setSortDirection(sortDirection: Boolean) {
        //Если текущие значения не совпадают, переворачиваем список
        if ((sortDirection && !this.sortDirection) || (!sortDirection && this.sortDirection)) {

            this.sortDirection = sortDirection
            sortDirectionData.value = sortDirection

            goodHabits.reverse()
            badHabits.reverse()

            goodHabitsData.value = goodHabits
            badHabitsData.value = badHabits
        }

        if (searchedText != "") {
            search(searchedText)
        }
    }

    fun search(text: String) {

        searchedText = text.toLowerCase(Locale.ROOT)

        val filterElement = FilterElement(
            FilterElement.Type.SEARCH
        )

        val searchFilters = (filterElements as List<FilterElement>).filter {
            it.type == FilterElement.Type.SEARCH
        }

        filterElements.removeAll(searchFilters)

        if (text == ""){
            filterElementData.value = filterElements
        } else {
            filterElements.add(filterElement)
            filterElementData.value = filterElements
        }

        val searchedGoodHabits = goodHabits.filter {
            it.title.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))
        }

        val searchedBadHabits = badHabits.filter {
            it.title.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))
        }

        goodHabitsData.value = searchedGoodHabits as ArrayList<Habit>
        badHabitsData.value = searchedBadHabits as ArrayList<Habit>
    }

    fun setDoneDate(habit: Habit) {

        viewModelScope.launch {
            val doneDate = Calendar.getInstance().timeInMillis.toString()

            habit.doneDates = habit.doneDates?.plus(doneDate)
            editHabitUseCase.execute(habit)

            val habitState = doneDatesCheckUseCase.execute(habit)

            habitStateData.value = Event(habitState)
        }
    }

    fun sortHabits(sort: Sort) {

        sortType = sort

        val filterElement = FilterElement(
            FilterElement.Type.SORT,
        )

        val filters = (filterElements as List<FilterElement>).filter {
            it.type == FilterElement.Type.SORT
        }

        filterElements.removeAll(filters)

        if (sort != Sort.NONE) {
            filterElements.add(filterElement)
            filterElementData.value = filterElements
        } else {
            filterElementData.value = filterElements
        }

        when (sort) {
            Sort.NONE -> {
                val (goodHabits, badHabits) = splitHabits(habits as MutableList<Habit>)
                setHabitsAfterSort(goodHabits.reversed(), badHabits.reversed())
            }
            Sort.BY_PRIORITY -> sortByPriority()
            Sort.BY_NUMBER_OF_TIMES -> sortByNumberOfTimes()
            Sort.BY_PERIOD -> sortByPeriod()
        }

        if (searchedText != "") {
            search(searchedText)
        }
    }

    private fun sortByPriority() {

        val (goodHabits, badHabits) = splitHabits(habits)

        val sortedGoodHabits = goodHabits.sortedBy {
            it.priority
        }

        val sortedBadHabits = badHabits.sortedBy {
            it.priority
        }

        setHabitsAfterSort(sortedGoodHabits, sortedBadHabits)
    }

    private fun sortByNumberOfTimes() {

        val (goodHabits, badHabits) = splitHabits(habits)

        val sortedGoodHabits = goodHabits.sortedBy {
            when (it.frequency) {
                Frequency.A_DAY -> it.count * 365.0
                Frequency.A_WEEK -> it.count * 52.134
                Frequency.A_MONTH -> it.count * 12.0
                Frequency.A_YEAR -> it.count.toDouble()

                else -> it.count.toDouble()
            }
        }

        val sortedBadHabits = badHabits.sortedBy {
            when (it.frequency) {
                Frequency.A_DAY -> it.count * 365.0
                Frequency.A_WEEK -> it.count * 52.134
                Frequency.A_MONTH -> it.count * 12.0
                Frequency.A_YEAR -> it.count.toDouble()

                else -> it.count.toDouble()
            }
        }

        setHabitsAfterSort(sortedGoodHabits, sortedBadHabits)
    }

    private fun sortByPeriod() {

        val (goodHabits, badHabits) = splitHabits(habits)

        val sortedGoodHabits = goodHabits.sortedBy {
            it.frequency
        }

        val sortedBadHabits = badHabits.sortedBy {
            it.frequency
        }

        setHabitsAfterSort(sortedGoodHabits, sortedBadHabits)
    }

    private fun setHabitsAfterSort(sortedGoodHabits: List<Habit>, sortedBadHabits: List<Habit>) {
        if (sortDirection) {

            val goodHabits = ArrayList<Habit>(sortedGoodHabits)
            goodHabits.reverse()

            this.goodHabits = goodHabits
            goodHabitsData.value = goodHabits

            val badHabits = ArrayList<Habit>(sortedBadHabits)
            badHabits.reverse()

            this.badHabits = badHabits
            badHabitsData.value = badHabits
        } else {
            val goodHabits = ArrayList<Habit>(sortedGoodHabits)

            this.goodHabits = goodHabits
            goodHabitsData.value = goodHabits

            val badHabits = ArrayList<Habit>(sortedBadHabits)

            this.badHabits = badHabits
            badHabitsData.value = badHabits
        }
    }

    private fun checkSortDirection(habits: ArrayList<Habit>): ArrayList<Habit> {
        return if (this.sortDirection) {
            habits
        } else {
            val reversedHabits = habits.reversed()
            reversedHabits as ArrayList<Habit>
        }
    }

    private fun splitHabits(habits: List<Habit>): Pair<List<Habit>, List<Habit>> =
        habits.partition {
            when (it.type) {
                Type.GOOD -> true
                Type.BAD -> false
                else -> true
            }
        }
}