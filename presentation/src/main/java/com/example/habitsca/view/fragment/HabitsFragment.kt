package com.example.habitsca.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habitsca.R
import com.example.habitsca.adapter.HabitsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_habits.*

open class HabitsFragment: Fragment(R.layout.fragment_habits) {

    lateinit var habitsRecyclerViewAdapter: HabitsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitsRecyclerViewAdapter =
            HabitsRecyclerViewAdapter(ArrayList()) {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToEditFragment(it)
                findNavController().navigate(action)
            }

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = habitsRecyclerViewAdapter
    }
}