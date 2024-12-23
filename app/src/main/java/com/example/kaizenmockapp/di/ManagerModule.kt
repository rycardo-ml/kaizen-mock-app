package com.example.kaizenmockapp.di

import com.example.kaizenmockapp.util.SerializationManager
import com.example.kaizenmockapp.util.TimeManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ManagerModule {

    @Provides
    @Singleton
    fun providersSerializableManager(): SerializationManager {
        return SerializationManager()
    }

    @Provides
    @Singleton
    fun providersTimeManager(): TimeManager {
        return TimeManager()
    }
}