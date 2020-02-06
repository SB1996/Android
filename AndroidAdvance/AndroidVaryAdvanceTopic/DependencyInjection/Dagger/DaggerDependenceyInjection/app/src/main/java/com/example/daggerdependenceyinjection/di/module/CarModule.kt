package com.example.daggerdependenceyinjection.di.module

import com.example.daggerdependenceyinjection.data.component.Engine
import com.example.daggerdependenceyinjection.data.component.Wheel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CarModule {

    @Singleton
    @Provides
    fun engineProvider() : Engine{ return Engine() }

    @Singleton
    @Provides
    fun wheelProvider() : Wheel{ return Wheel() }


}