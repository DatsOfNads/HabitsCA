package com.example.habitsca.view.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.example.habitsca.App
import com.example.habitsca.view.viewmodel.AddFragmentModel
import kotlinx.android.synthetic.main.fragment_add_and_edit.*
import javax.inject.Inject

class AddFragment: AddAndEditFragment() {

    @Inject lateinit var model: AddFragmentModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as App)
            .component
            .build()
            .inject(this)

        floatingActionButton.setOnClickListener {

            val habit = createNewHabit() ?: return@setOnClickListener

            model.addHabit(habit)
            Navigation.findNavController(requireView()).popBackStack()
        }
    }
}