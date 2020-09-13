package com.example.habitsca.module

import androidx.fragment.app.FragmentActivity
import com.example.habitsca.view.adapter.ViewPagerAdapter
import com.example.habitsca.view.fragment.BadHabitsFragment
import com.example.habitsca.view.fragment.GoodHabitsFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HomeFragmentModule(
    private val activity: FragmentActivity
) {

    @Provides
    @Singleton
    fun provideViewPagerAdapter(activity: FragmentActivity): ViewPagerAdapter {

        val fragmentList = arrayListOf(
            GoodHabitsFragment(),
            BadHabitsFragment()
        )

        return ViewPagerAdapter(fragmentList,activity)
    }

    @Singleton
    @Provides
    fun provideFragmentActivity(): FragmentActivity{
        return activity
    }
}