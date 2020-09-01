package com.example.habitsca.module

import android.app.Application
import com.example.habitsca.view.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ServerModule::class, DatabaseModule::class, ContextModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}