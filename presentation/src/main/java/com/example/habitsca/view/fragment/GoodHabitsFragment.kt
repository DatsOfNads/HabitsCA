package com.example.habitsca.view.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.navGraphViewModels
import com.example.domain.model.Habit
import com.example.habitsca.App
import com.example.habitsca.R
import com.example.habitsca.view.viewmodel.HomeModel
import com.example.habitsca.view.viewmodel.factory.HomeModelFactory
import javax.inject.Inject

class GoodHabitsFragment: HabitsFragment() {

    @Inject
    lateinit var viewModelFactory: HomeModelFactory

    private val model: HomeModel by navGraphViewModels(R.id.my_nav){
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as App)
            .component
            .build()
            .inject(this)

       // model.search("сабачку")

        model.subscribeGoodHabits().observe(viewLifecycleOwner, {
            habitsRecyclerViewAdapter.addAll(it as ArrayList<Habit>)
        })
    }
}