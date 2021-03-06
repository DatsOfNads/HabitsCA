package com.example.habitsca.module

import android.content.Context
import androidx.room.Room
import com.example.data.database.HabitsDao
import com.example.data.database.HabitsDatabase
import com.example.data.mapper.HabitsMapper
import com.example.data.repository.DatabaseRepositoryImpl
import com.example.domain.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseRepository(habitsDao: HabitsDao, habitsMapper: HabitsMapper): DatabaseRepository{
        return DatabaseRepositoryImpl(habitsDao, habitsMapper)
    }

    @Provides
    @Singleton
    fun provideHabitsDao(context: Context): HabitsDao {
        return getDatabase(context).habitsDao()
    }

    companion object{
        @Volatile
        private var INSTANCE: HabitsDatabase? = null

        fun getDatabase(context: Context): HabitsDatabase{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitsDatabase::class.java,
                    "habits_data"
                ).allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}