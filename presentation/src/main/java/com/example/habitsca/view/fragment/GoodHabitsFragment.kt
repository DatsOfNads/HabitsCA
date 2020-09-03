package com.example.habitsca.view.fragment

import android.os.Bundle
import android.view.View
import com.example.habitsca.App
import com.example.habitsca.view.viewmodel.GoodHabitsFragmentModel
import kotlinx.android.synthetic.main.fragment_habits.*
import javax.inject.Inject

class GoodHabitsFragment: HabitsFragment() {

    @Inject lateinit var model: GoodHabitsFragmentModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as App)
            .component
            .build()
            .inject(this)

        model.subscribeAllData.observe(viewLifecycleOwner, {
            textView.text = it.toString()
        })
    }
}