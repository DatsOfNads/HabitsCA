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
                uid = "bob",
                title = "Вещаю из CA",
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
            val uid = "dae6e801-3eca-4fe0-9f7d-9de88fd95d77"
            viewModel.deleteHabit(uid)
        }

        viewModel.subscribeAllHabits.observe(this, {
            textView.text = it.toString()
        })
    }
}