package com.example.habitsca

import android.app.Application
import com.example.habitsca.module.ApplicationComponent
import com.example.habitsca.module.DaggerApplicationComponent

open class App: Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.create()
    }
}