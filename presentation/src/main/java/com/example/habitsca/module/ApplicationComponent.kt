package com.example.habitsca.module

import com.example.habitsca.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}