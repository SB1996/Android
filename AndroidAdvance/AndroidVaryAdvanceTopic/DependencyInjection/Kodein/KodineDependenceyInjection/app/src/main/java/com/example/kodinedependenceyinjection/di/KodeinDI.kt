package com.example.kodinedependenceyinjection.di

import com.example.kodinedependenceyinjection.bike.Bike
import com.example.kodinedependenceyinjection.bike.component.Engine
import com.example.kodinedependenceyinjection.bike.component.Tool
import com.example.kodinedependenceyinjection.bike.component.Wheel
import com.example.kodinedependenceyinjection.bike.subComponent.engine.DieselEngine
import com.example.kodinedependenceyinjection.bike.subComponent.engine.ElectricEngine
import com.example.kodinedependenceyinjection.bike.subComponent.engine.PetrolEngine
import com.example.kodinedependenceyinjection.bike.subComponent.tools.Tools
import com.example.kodinedependenceyinjection.bike.subComponent.wheel.Rim
import com.example.kodinedependenceyinjection.bike.subComponent.wheel.Tire
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val mainKodeinDependenceInjection = Kodein.Module{

    /** Engine Component **/
    bind() from singleton { PetrolEngine() }
    bind() from singleton { DieselEngine() }
    bind() from singleton { ElectricEngine() }

    /** Wheel Component **/
    bind() from singleton { Rim() }
    bind() from singleton { Tire() }

    /** Tools Component **/
    bind() from singleton { Tools() }

    /** Engine Instance **/
    bind() from  singleton { Engine(instance(), instance(), instance()) }

    /** Wheel Instance **/
    bind() from singleton { Wheel(instance(), instance()) }

    /** Tool Instance **/
    bind() from  singleton { Tool(instance()) }

    /** Bike Instance **/
    bind() from singleton { Bike(instance(), instance(), instance()) }
}
