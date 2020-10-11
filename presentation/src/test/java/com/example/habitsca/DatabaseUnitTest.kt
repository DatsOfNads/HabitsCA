package com.example.habitsca

import com.example.domain.model.Habit
import com.example.domain.model.`object`.Frequency
import com.example.domain.model.`object`.Priority
import com.example.domain.model.`object`.Type
import com.example.domain.repository.DatabaseRepository
import com.example.domain.usecase.database.AddHabitUseCase
import com.example.domain.usecase.database.DeleteHabitUseCase
import com.example.domain.usecase.database.EditHabitUseCase
import com.example.domain.usecase.database.GetAllDataUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class DatabaseUnitTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope =
        MainCoroutineScopeRule()

    @Mock
    lateinit var databaseRepository: DatabaseRepository

    @Before
    fun before(){
        MockitoAnnotations.openMocks(this)

        addHabitUseCase = AddHabitUseCase(databaseRepository)
        editHabitUseCase = EditHabitUseCase(databaseRepository)
        deleteHabitUseCase = DeleteHabitUseCase(databaseRepository)
        getAllDataUseCase = GetAllDataUseCase(databaseRepository)
    }

    @Mock
    lateinit var addHabitUseCase: AddHabitUseCase

    @Test
    fun addHabitUseCaseTest(){

        val fakeDB = mutableListOf<Habit>()
        val mockedHabit = mock(Habit::class.java)

        runBlocking {
            `when`(databaseRepository.addHabit(mockedHabit)).thenAnswer {
                fakeDB.add(mockedHabit)
            }

            addHabitUseCase.execute(mockedHabit)
            Assert.assertEquals(mockedHabit, fakeDB[0])
        }
    }

    @Mock
    lateinit var editHabitUseCase: EditHabitUseCase

    @Test
    fun editHabitUseCaseTest(){
        val fakeDB = mutableListOf<Habit>()
        val habit = Habit(
            "some id",
            "some title",
            "some description",
            Priority.LOW,
            Type.GOOD,
            0,
            Frequency.A_DAY,
            0,
            listOf()
        )

        fakeDB.add(habit)

        runBlocking {
            `when`(databaseRepository.editHabit(habit)).thenAnswer {
                habit.title = "another title"
                fakeDB.set(0,habit)
            }

            editHabitUseCase.execute(habit)
            Assert.assertEquals(habit, fakeDB[0])
        }
    }

    @Mock
    lateinit var deleteHabitUseCase: DeleteHabitUseCase

    @Test
    fun deleteHabitUseCaseTest(){
        val fakeDB = mutableListOf<Habit>()
        val mockedHabit = mock(Habit::class.java)
        fakeDB.add(mockedHabit)

        runBlocking {
            `when`(databaseRepository.deleteHabit(mockedHabit)).thenAnswer {
                fakeDB.remove(mockedHabit)
            }

            deleteHabitUseCase.execute(mockedHabit)
            Assert.assertEquals(0, fakeDB.size)
        }
    }

    @Mock
    lateinit var getAllDataUseCase: GetAllDataUseCase

    @Test
    fun getAllDataUseCaseTest(){
        val fakeDB = mutableListOf<Habit>()
        val mockedHabit = mock(Habit::class.java)

        fakeDB.add(mockedHabit)
        fakeDB.add(mockedHabit)

        runBlocking {
            `when`(databaseRepository.getAllData()).thenReturn(fakeDB)

            val data = getAllDataUseCase.execute()
            Assert.assertEquals(fakeDB, data)
        }
    }
}