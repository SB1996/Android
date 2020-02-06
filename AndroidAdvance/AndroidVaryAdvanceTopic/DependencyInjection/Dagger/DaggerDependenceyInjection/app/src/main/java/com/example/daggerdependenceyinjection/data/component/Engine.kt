package com.example.daggerdependenceyinjection.data.component

import com.example.daggerdependenceyinjection.data.component.engineSubComponent.DieselEngine
import com.example.daggerdependenceyinjection.data.component.engineSubComponent.ElectricEngine
import com.example.daggerdependenceyinjection.data.component.engineSubComponent.PetrolEngine
import com.example.daggerdependenceyinjection.di.component.DaggerEngineComponent
import com.example.daggerdependenceyinjection.di.component.EngineComponent
import javax.inject.Inject
import android.util.Log

class Engine {

    private val TAG: String = Engine::class.java.simpleName

    /*** Engine Component ***/
    private lateinit var engineComponent : EngineComponent


    /** Engine Data **/
    lateinit var engineType: String
    var isEngineConfigure: Boolean = false

    /** Engine Component Data **/
    @Inject lateinit var petrolEngine: PetrolEngine
    @Inject lateinit var dieselEngine: DieselEngine
    @Inject lateinit var electricEngine: ElectricEngine

    internal fun setEngine(type: String){

        engineComponent = DaggerEngineComponent.builder().build()

        when(type){
            "Petrol Engine" -> {
                engineComponent.petrolEngineInjection(this)
                engineType = petrolEngine.engineType
            }
            "Diesel Engine" -> {
                engineComponent.dieselEngineInjection(this)
                engineType = dieselEngine.engineType
            }
            "Electric Engine" -> {
                engineComponent.electricEngineInjection(this)
                engineType = electricEngine.engineType
            }

            else -> {
                engineComponent.petrolEngineInjection(this)
                engineType = petrolEngine.engineType
            }
        }
        isEngineConfigure = true
        Log.d(TAG, "setEngine : COMPLETED")
    }

    internal fun engineConfiguration(engineType: String){
        this.setEngine(engineType)

        when(engineType) {
            petrolEngine.engineType -> {
                if(!petrolEngine.isEngineReady){
                    petrolEngine.doReadyEngine()
                }
            }
            dieselEngine.engineType -> {
                if(!dieselEngine.isEngineReady){
                    dieselEngine.doReadyEngine()
                }
            }
            electricEngine.engineType -> {
                if(!electricEngine.isEngineReady){
                    electricEngine.doReadyEngine()
                }
            }

            else -> {  }
        }

        Log.d(TAG, "engineConfiguration : DONE")
        this.engineInformation()
    }

    internal fun engineInformation(){
        Log.d(TAG, "engineInformation ::  ")

        when(engineType) {
            petrolEngine.engineType -> {
                Log.d(TAG, "Engine Type: ${petrolEngine.engineType}")
            }
            dieselEngine.engineType -> {
                Log.d(TAG, "Engine Type: ${dieselEngine.engineType}")
            }
            electricEngine.engineType -> {
                Log.d(TAG, "Engine Type: ${electricEngine.engineType}")
            }

            else -> {  }
        }
    }
}