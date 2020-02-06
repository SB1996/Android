package com.example.daggerdependenceyinjection.di.module

import com.example.daggerdependenceyinjection.data.component.wheelSubComponent.Tire
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TireModule {

    @Singleton
    @Provides
    fun tireProvider() : Tire {
        return Tire()
    }
}