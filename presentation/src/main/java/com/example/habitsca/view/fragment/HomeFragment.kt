package com.example.habitsca.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.habitsca.App
import com.example.habitsca.R
import com.example.habitsca.adapter.ViewPagerAdapter
import com.example.habitsca.module.HomeFragmentModule
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home_content.*
import javax.inject.Inject

class HomeFragment: Fragment(R.layout.fragment_home) {

    @Inject lateinit var viewPagerAdapter: ViewPagerAdapter

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
    }

}