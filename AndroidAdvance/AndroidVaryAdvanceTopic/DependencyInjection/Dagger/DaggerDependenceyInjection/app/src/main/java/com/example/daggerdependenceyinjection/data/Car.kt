package com.example.daggerdependenceyinjection.data

import android.util.Log
import com.example.daggerdependenceyinjection.data.component.Engine
import com.example.daggerdependenceyinjection.data.component.Wheel
import com.example.daggerdependenceyinjection.di.component.CarComponent
import com.example.daggerdependenceyinjection.di.component.DaggerCarComponent
import javax.inject.Inject

class Car {

    private val TAG: String = Car::class.java.simpleName

    /** Car Engine Details **/
    private val engineType: String = "Petrol Engine"

    /** Car Wheel Details **/
    private val wheelType: String = "Normal Wheel"

    /** Rim Data **/
    private val rimType: String = "Default Rim"

    /** Tire Data **/
    private val trieCompanies: String = "MRF Tire"
    private val trieType: String = "Normal Trie"
    private val trieSize: String = "Default Trie"

    /** Car Data **/
    var isCarReady: Boolean = false


    /*** Car Component ***/
    private lateinit var carComponent: CarComponent

    @Inject lateinit var engine: Engine
    @Inject lateinit var wheel: Wheel

    /** Start Assemble Car **/
    fun assembleCar(){
        carComponent = DaggerCarComponent.builder().build()

        carComponent.engineInjection(this)
        engineAssembler()

        carComponent.wheelInjection(this)
        wheelAssembler()

        isCarReady = true
    }

    fun engineAssembler(){
        engine.engineConfiguration(engineType)
    }
    fun wheelAssembler(){
        wheel.wheelConfiguration()
    }

    internal fun drive(){
        if (isCarReady){
            Log.d(TAG, "drive: Car is deriving by me, .....................VHOOOOOOOOM.....................")
        }else{
            Log.d(TAG, "drive: Car is not ready yet for driving")
        }
    }
}