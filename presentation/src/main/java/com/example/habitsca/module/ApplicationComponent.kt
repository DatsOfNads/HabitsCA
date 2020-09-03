package com.example.habitsca.module

import com.example.habitsca.view.fragment.AddFragment
import com.example.habitsca.view.fragment.GoodHabitsFragment
import com.example.habitsca.view.fragment.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ServerModule::class, DatabaseModule::class, ContextModule::class, HomeFragmentModule::class])
interface ApplicationComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(goodHabitsFragment: GoodHabitsFragment)
    fun inject(addFragment: AddFragment)
}