package com.example.daggerdependenceyinjection.di.module

import com.example.daggerdependenceyinjection.data.component.engineSubComponent.DieselEngine
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DieselEngineModule {

    @Singleton
    @Provides
    fun DieselEngineProvider() : DieselEngine {
        return DieselEngine()
    }
}