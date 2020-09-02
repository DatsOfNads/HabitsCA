package com.example.habitsca.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.domain.model.Habit
import com.example.habitsca.App
import com.example.habitsca.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).component.inject(this)

        buttonGet.setOnClickListener {
           //viewModel.getAllHabits()
            viewModel.getHabitsFromDatabase()
        }

        buttonSet.setOnClickListener {
            val habit = Habit(
                uid = "bobh",
                title = "Вещаю из CAl",
                description = "Тут сложна, но интересна",
                priority = 1,
                type = 0,
                count = 2,
                frequency = 0,
                date = 2020,
                doneDates = listOf()
            )

            //viewModel.putHabit(habit)
            viewModel.putHabitToDataBase(habit)
        }

        buttonDelete.setOnClickListener {

        }

        viewModel.subscribeAllHabits.observe(this, {
            textView.text = it.toString()
        })
    }
}