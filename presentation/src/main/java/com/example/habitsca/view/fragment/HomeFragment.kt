package com.example.habitsca.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionManager
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.domain.model.FilterElement
import com.example.domain.model.Sort
import com.example.domain.model.`object`.Type
import com.example.habitsca.App
import com.example.habitsca.R
import com.example.habitsca.adapter.ViewPagerAdapter
import com.example.habitsca.module.HomeFragmentModule
import com.example.habitsca.view.viewmodel.HomeFragmentModel
import com.example.habitsca.view.viewmodel.factory.HomeModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_content.*
import javax.inject.Inject


class HomeFragment: Fragment(R.layout.fragment_home) {

    @Inject lateinit var viewPagerAdapter: ViewPagerAdapter

    @Inject
    lateinit var viewModelFactory: HomeModelFactory

    private val model: HomeFragmentModel by navGraphViewModels(R.id.my_nav){
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().let {
            (activity?.application as App)
                .component
                .homeFragmentModule(HomeFragmentModule(it))
                .build()
                .inject(this)
        }

        initViewPager()
        initBottomSheet()
        initChipGroup()
        initEditTexts()
        initButtons()
    }

    private fun initViewPager(){

        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            when(position){
                0 -> tab.text = "Хорошие"
                else -> tab.text = "Плохие"
            }
        }.attach()
    }

    private fun initBottomSheet(){

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                floatingActionButton.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset)
                    .setDuration(
                        0
                    ).start()
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //Если bottom sheet задвинут, то кнопка работает. Иначе - нет.
                floatingActionButton.isClickable = newState == BottomSheetBehavior.STATE_COLLAPSED
            }
        })

        constraintLayoutTitle.setOnClickListener {
            if(bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED){
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun initChipGroup(){

        chipSearch.setOnCloseIconClickListener {
            editTextSearch.text?.clear()
        }

        chipFilter.setOnCloseIconClickListener {
            autoCompleteTextViewSort.setText(resources.getText(R.string.sort_none))
        }

        model.subscribeFilterElement().observe(viewLifecycleOwner, {filterElements ->

            var isSearch = false
            var isSort = false

            filterElements.forEach {
                if(it.type == FilterElement.Type.SEARCH){
                    isSearch = true
                }else{
                    isSort = true
                }
            }

            val autoTransition = android.transition.AutoTransition()
            autoTransition.duration = 200

            if(isSearch){
                TransitionManager.beginDelayedTransition(bottomSheet, autoTransition)
                chipSearch.visibility = View.VISIBLE
            } else{
                TransitionManager.beginDelayedTransition(bottomSheet, autoTransition)
                chipSearch.visibility = View.GONE
            }

            if(isSort){
                TransitionManager.beginDelayedTransition(bottomSheet, autoTransition)
                chipFilter.visibility = View.VISIBLE
            }else{
                TransitionManager.beginDelayedTransition(bottomSheet, autoTransition)
                chipFilter.visibility = View.GONE
            }
        })
    }

    private fun initEditTexts(){

        //EditTextSearch
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.search(text.toString())
            }
        })

        //EditTextSort
        val adapterSort = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayOf(
                resources.getString(R.string.sort_none),
                resources.getString(R.string.sort_by_priority),
                resources.getString(R.string.sort_by_number_of_times),
                resources.getString(R.string.sort_by_period)
            )
        )

        autoCompleteTextViewSort.setAdapter(adapterSort)

        autoCompleteTextViewSort.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when (p0.toString()) {
                    resources.getString(R.string.sort_none) -> model.sortHabits(
                        Sort.NONE
                    )
                    resources.getString(R.string.sort_by_priority) -> model.sortHabits(
                        Sort.BY_PRIORITY
                    )
                    resources.getString(R.string.sort_by_number_of_times) -> model.sortHabits(
                        Sort.BY_NUMBER_OF_TIMES
                    )
                    resources.getString(R.string.sort_by_period) -> model.sortHabits(
                        Sort.BY_PERIOD
                    )
                }
            }

        })
    }

    private fun initButtons(){

        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

        model.subscribeSortDirection().observe(viewLifecycleOwner, {
            if (it) {
                imageButtonUp.setImageResource(R.drawable.icon_arrow_upward_gray_36dp)
                imageButtonDown.setImageResource(R.drawable.icon_arrow_downward_36dp)

            } else {
                imageButtonUp.setImageResource(R.drawable.icon_arrow_upward_36dp)
                imageButtonDown.setImageResource(R.drawable.icon_arrow_downward_gray_36dp)
            }
        })

        imageButtonUp.setOnClickListener {
            model.setSortDirection(true)
        }

        imageButtonDown.setOnClickListener {
            model.setSortDirection(false)
        }

        model.subscribeHabitState().observe(viewLifecycleOwner, {event ->

            event.getContentIfNotHandled()?.let { habitState ->
                val type = habitState.type
                val count = habitState.count
                val doneCount = habitState.doneCount

                System.err.println("Количество выполненных: $doneCount")

                if (type == Type.GOOD) {
                    if (doneCount < count) {
                        val deferenceBetweenValuesPlural = resources.getQuantityString(
                            R.plurals.plurals_count,
                            count - doneCount,
                            count - doneCount
                        )

                        Toast.makeText(
                            requireContext(),
                            "Стоит выполнить ещё $deferenceBetweenValuesPlural",
                            Toast.LENGTH_SHORT).show()
                    }

                    if (doneCount == count) {
                        Toast.makeText(
                            requireContext(),
                            "Ты выполнил план!",
                            Toast.LENGTH_SHORT).show()
                    }

                    if (doneCount > count) {
                        Toast.makeText(
                            requireContext(),
                            "You are breathtaking!",
                            Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (doneCount < count) {
                        val deferenceBetweenValuesPlural = resources.getQuantityString(
                            R.plurals.plurals_count,
                            count - doneCount,
                            count - doneCount
                        )

                        Toast.makeText(
                            requireContext(),
                            "Можете выполнить ещё $deferenceBetweenValuesPlural",
                            Toast.LENGTH_SHORT).show()
                    }

                    if (doneCount == count) {
                        Toast.makeText(
                            requireContext(),
                            "Больше нельзя этого делать",
                            Toast.LENGTH_SHORT).show()
                    }

                    if (doneCount > count) {
                        Toast.makeText(
                            requireContext(),
                            "Хватит это делать!",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}