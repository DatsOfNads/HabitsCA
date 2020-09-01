package com.example.habitsca.module

import com.example.data.ServerApi
import com.example.data.ServerEndpoint
import com.example.data.mapper.HabitsMapper
import com.example.data.repository.ServerRepositoryImpl
import com.example.domain.repository.ServerRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    fun provideServerApi(serverEndpoint: ServerEndpoint): ServerApi{
        return ServerApi(serverEndpoint)
    }

    @Provides
    @Singleton
    fun provideServerEndpoint(retrofit: Retrofit): ServerEndpoint{
        return retrofit.create(ServerEndpoint::class.java)
    }

    @Provides
    fun provideServerRepository(serverApi: ServerApi, habitsMapper: HabitsMapper): ServerRepository{
        return ServerRepositoryImpl(serverApi, habitsMapper)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}