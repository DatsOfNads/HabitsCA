package com.example.habitsca

import android.app.Application
import com.example.habitsca.module.ContextModule
import com.example.habitsca.module.DaggerApplicationComponent

open class App: Application() {

    lateinit var component: DaggerApplicationComponent.Builder

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder().contextModule(ContextModule(applicationContext))
    }
}