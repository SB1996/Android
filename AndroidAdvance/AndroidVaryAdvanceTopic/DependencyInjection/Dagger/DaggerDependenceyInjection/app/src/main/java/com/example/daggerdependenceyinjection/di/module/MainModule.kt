package com.example.daggerdependenceyinjection.di.module

import com.example.daggerdependenceyinjection.data.Car
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule {

    @Singleton
    @Provides
    fun carProvider() : Car{
        return Car()
    }
}