package com.example.habitsca.view.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habitsca.App
import com.example.habitsca.R
import com.example.habitsca.adapter.ViewPagerAdapter
import com.example.habitsca.module.HomeFragmentModule
import com.example.habitsca.view.viewmodel.AddFragmentModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_content.*
import javax.inject.Inject

class HomeFragment: Fragment(R.layout.fragment_home) {

    @Inject lateinit var viewPagerAdapter: ViewPagerAdapter
    @Inject lateinit var model: AddFragmentModel

    override fun onStart() {
        super.onStart()
        initBottomSheet()
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

        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout,viewPager){tab, position ->
            when(position){
                0 -> tab.text = "Хорошие"
                else -> tab.text = "Плохие"
            }
        }.attach()

        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
    }

    private fun initBottomSheet(){

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                floatingActionButton.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start()
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //Если bottom sheet задвинут, то кнопка работает. Иначе - нет.
                floatingActionButton.isClickable = newState == BottomSheetBehavior.STATE_COLLAPSED
            }
        })

        bottomSheet.viewTreeObserver.addOnGlobalLayoutListener (object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                bottomSheet.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val hidden = bottomSheet.getChildAt(0)
                bottomSheetBehavior.peekHeight = hidden.bottom
            }
        })

        constraintLayoutTitle.setOnClickListener {
            if(bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED){
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

}