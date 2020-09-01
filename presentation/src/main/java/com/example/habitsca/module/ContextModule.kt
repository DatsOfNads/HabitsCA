package com.example.habitsca.module

import android.content.Context
import dagger.Module
import dagger.Provides


@Module
class ContextModule(var context: Context) {

    @Provides
    fun context(): Context {
        return context.applicationContext
    }
}