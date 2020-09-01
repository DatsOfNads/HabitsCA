package com.example.habitsca.module

import com.example.habitsca.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ServerModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}