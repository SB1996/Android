package com.example.daggerdependenceyinjection.di.module

import com.example.daggerdependenceyinjection.data.component.engineSubComponent.ElectricEngine
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ElectricEngineModule {

    @Singleton
    @Provides
    fun ElectricEngineProvider() : ElectricEngine {
        return ElectricEngine()
    }
}