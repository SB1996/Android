package com.example.daggerdependenceyinjection.di.module

import com.example.daggerdependenceyinjection.data.component.engineSubComponent.PetrolEngine
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PetrolEngineModule {

    @Singleton
    @Provides
    fun PetrolEngineProvider() : PetrolEngine {
        return PetrolEngine()
    }
}