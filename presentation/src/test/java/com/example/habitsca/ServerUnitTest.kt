package com.example.habitsca

import com.example.domain.model.Habit
import com.example.domain.model.HabitDone
import com.example.domain.repository.ServerRepository
import com.example.domain.usecase.server.DeleteHabitUseCase
import com.example.domain.usecase.server.GetAllHabitsUseCase
import com.example.domain.usecase.server.PutAllHabitsUseCase
import com.example.domain.usecase.server.PutHabitUseCase
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
class ServerUnitTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope =
        MainCoroutineScopeRule()

    @Mock
    lateinit var serverRepository: ServerRepository

    @Before
    fun before(){
        MockitoAnnotations.openMocks(this)
        getAllHabitsUseCase = GetAllHabitsUseCase(serverRepository)
        putHabitUseCase = PutHabitUseCase(serverRepository)
        deleteHabitUseCase = DeleteHabitUseCase(serverRepository)
        putAllHabitUseCase = PutAllHabitsUseCase(serverRepository)
    }

    @Mock
    lateinit var getAllHabitsUseCase: GetAllHabitsUseCase

    @Test
    fun getAllHabitsTest(){
        val fakeServer = mutableListOf<Habit>()
        val mockedHabit = mock(Habit::class.java)

        fakeServer.add(mockedHabit)
        fakeServer.add(mockedHabit)

        runBlocking {
            `when`(serverRepository.getAllHabits()).thenReturn(Pair(fakeServer,0))

            val response = getAllHabitsUseCase.execute().first

            Assert.assertEquals(fakeServer, response)
        }
    }

    @Mock
    lateinit var putHabitUseCase: PutHabitUseCase

    @Test
    fun putHabitUseCaseTest(){
        val fakeServer = mutableListOf<Habit>()
        val mockedHabit = mock(Habit::class.java)

        runBlocking {
            `when`(serverRepository.putHabit(mockedHabit)).thenAnswer {
                fakeServer.add(mockedHabit)
            }

            putHabitUseCase.execute(mockedHabit)

            Assert.assertEquals(mockedHabit, fakeServer[0])
        }
    }

    @Mock
    lateinit var deleteHabitUseCase: DeleteHabitUseCase

    @Test
    fun deleteHabitUseCaseTest(){
        val fakeServer = mutableListOf<Habit>()
        val mockedHabit = mock(Habit::class.java)

        fakeServer.add(mockedHabit)

        runBlocking {
            `when`(serverRepository.deleteHabit("some id")).thenAnswer {
                fakeServer.remove(mockedHabit)
            }

           deleteHabitUseCase.execute("some id")

            Assert.assertEquals(fakeServer.size, 0)
        }
    }

    @Mock
    lateinit var putAllHabitUseCase: PutAllHabitsUseCase

    @Suppress("UNCHECKED_CAST")
    @Test
    fun putAllHabitUseCase(){
        val mockedHabit = mock(Habit::class.java)

        val mockedOldHabits = mock(MutableList::class.java) as MutableList<Habit>
        `when`(mockedOldHabits.size).thenReturn(3)

        `when`(mockedOldHabits[0]).thenReturn(mockedHabit)
        `when`(mockedOldHabits[1]).thenReturn(mockedHabit)
        `when`(mockedOldHabits[2]).thenReturn(mockedHabit)

        `when`(mockedOldHabits[0].uid).thenReturn("some id")
        `when`(mockedOldHabits[1].uid).thenReturn("some id")
        `when`(mockedOldHabits[2].uid).thenReturn("some id")

        val mockedNewHabits = mock(MutableList::class.java) as MutableList<Habit>
        `when`(mockedNewHabits.size).thenReturn(2)

        `when`(mockedNewHabits[0]).thenReturn(mockedHabit)
        `when`(mockedNewHabits[1]).thenReturn(mockedHabit)

        runBlocking {
            `when`(serverRepository.deleteHabit("some id")).thenReturn(Pair(Unit, 200))
            `when`(serverRepository.postHabitDoneDate(HabitDone(0, "some uid"))).thenReturn(Pair(Unit, 200))
            `when`(serverRepository.putHabit(mockedHabit)).thenReturn(Pair("some",200))

            val result = putAllHabitUseCase.execute(mockedNewHabits,mockedOldHabits)

            Assert.assertEquals(result, true)
        }
    }
}