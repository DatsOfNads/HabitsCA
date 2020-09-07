package com.example.habitsca.view.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.navGraphViewModels
import com.example.domain.model.Habit
import com.example.habitsca.App
import com.example.habitsca.R
import com.example.habitsca.view.viewmodel.HomeFragmentModel
import com.example.habitsca.view.viewmodel.factory.HomeModelFactory
import javax.inject.Inject

class BadHabitsFragment: HabitsFragment() {

    @Inject
    lateinit var viewModelFactory: HomeModelFactory

    private val model: HomeFragmentModel by navGraphViewModels(R.id.my_nav){
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as App)
            .component
            .build()
            .inject(this)

        model.subscribeBadHabits().observe(viewLifecycleOwner, {
            habitsRecyclerViewAdapter.addAll(it as ArrayList<Habit>)
        })
    }

    override fun onDoneDateAdded(habit: Habit) {
        model.setDoneDate(habit)
    }
}