package com.example.daggerdependenceyinjection.di.component

import com.example.daggerdependenceyinjection.data.component.Engine
import com.example.daggerdependenceyinjection.di.module.DieselEngineModule
import com.example.daggerdependenceyinjection.di.module.ElectricEngineModule
import com.example.daggerdependenceyinjection.di.module.PetrolEngineModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [
    PetrolEngineModule::class,
    DieselEngineModule::class,
    ElectricEngineModule::class
])
interface EngineComponent {

    fun petrolEngineInjection(engine: Engine)
    fun dieselEngineInjection(engine: Engine)
    fun electricEngineInjection(engine: Engine)
}