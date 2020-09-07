package com.example.habitsca.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Habit
import com.example.habitsca.R
import com.example.habitsca.adapter.HabitsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_habits.*

abstract class HabitsFragment: Fragment(R.layout.fragment_habits) {

    lateinit var habitsRecyclerViewAdapter: HabitsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitsRecyclerViewAdapter = HabitsRecyclerViewAdapter(
            requireContext(),
            ArrayList(),
            ::onHabitEdit,
            ::onDoneDateAdded
        )

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = habitsRecyclerViewAdapter
    }

    private fun onHabitEdit(habit: Habit){
        val action =
            HomeFragmentDirections.actionHomeFragmentToEditFragment(habit)
        findNavController().navigate(action)
    }

    abstract fun onDoneDateAdded(habit: Habit)
}