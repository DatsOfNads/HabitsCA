package com.example.habitsca.view.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habitsca.view.fragment.HabitsFragment
import javax.inject.Inject

class ViewPagerAdapter @Inject constructor(
    fragments: ArrayList<HabitsFragment>,
    activity: FragmentActivity
): FragmentStateAdapter(activity) {

    private val fragmentList = fragments

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]
}