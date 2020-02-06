package com.example.daggerdependenceyinjection.di.component

import com.example.daggerdependenceyinjection.data.Car
import com.example.daggerdependenceyinjection.di.module.CarModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [
    CarModule::class
])
interface CarComponent {

    fun engineInjection(car: Car)
    fun wheelInjection(car: Car)
}