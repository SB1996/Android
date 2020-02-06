package com.example.daggerdependenceyinjection.di.module

import com.example.daggerdependenceyinjection.data.component.wheelSubComponent.Rim
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RimModule {

    @Singleton
    @Provides
    fun rimProvider() : Rim {
        return Rim()
    }
}