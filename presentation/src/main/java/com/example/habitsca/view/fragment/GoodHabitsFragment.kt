package com.example.habitsca.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import com.example.domain.model.Habit
import com.example.habitsca.App
import com.example.habitsca.R
import com.example.habitsca.view.viewmodel.HomeModel
import javax.inject.Inject

class GoodHabitsFragment: HabitsFragment() {

   // @Inject lateinit var model: HomeModel

   // @Inject lateinit var factory: HomeModelFactory

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val model: HomeModel by navGraphViewModels(R.id.my_nav){
        viewModelFactory
    }

//    val model: HomeModel by navGraphViewModels(R.id.my_nav) {
//        SavedStateViewModelFactory(requireActivity().application, this)
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as App)
            .component
            .build()
            .inject(this)
//
//        val model = ViewModelProvider(this,factory).get(HomeModel::class.java)

        //val model = ViewModelProvider(this, viewModelFactory).get(HomeModel::class.java)

        model.search("сабачку")

        model.subscribeGoodHabits().observe(viewLifecycleOwner, {
            habitsRecyclerViewAdapter.addAll(it as ArrayList<Habit>)
        })
    }
}