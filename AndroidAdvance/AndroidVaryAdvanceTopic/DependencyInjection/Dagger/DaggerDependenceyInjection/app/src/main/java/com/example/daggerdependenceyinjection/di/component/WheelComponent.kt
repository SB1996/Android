package com.example.daggerdependenceyinjection.di.component

import com.example.daggerdependenceyinjection.data.component.Wheel
import com.example.daggerdependenceyinjection.di.module.RimModule
import com.example.daggerdependenceyinjection.di.module.TireModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [
    RimModule::class,
    TireModule::class
])
interface WheelComponent {

    fun rimInjection(wheel : Wheel)
    fun tireInjection(wheel : Wheel)
}