package com.example.kodinedependenceyinjection.bike.component

import android.util.Log
import com.example.kodinedependenceyinjection.bike.subComponent.engine.DieselEngine
import com.example.kodinedependenceyinjection.bike.subComponent.engine.ElectricEngine
import com.example.kodinedependenceyinjection.bike.subComponent.engine.PetrolEngine

class Engine(
    val petrolEngine: PetrolEngine,
    val dieselEngine: DieselEngine,
    val electricEngine: ElectricEngine
) {

    private val TAG: String = Engine::class.java.simpleName

    internal var isEngineReady: Boolean = false
    private lateinit var selectedEngine: String


    internal fun selectEngine(type: String){
        selectedEngine = when(type){

            petrolEngine.name -> petrolEngine.name

            dieselEngine.name -> dieselEngine.name

            electricEngine.name -> electricEngine.name

            else -> {
                "No Engine Selected"
            }
        }

    }

    /** Config Engine Setup **/
    internal fun doReadyEngine(type: String) : Boolean{
        if (!isEngineReady){
            Log.d(TAG, "doReadyEngine: Engine is ready.")
            isEngineReady = true
        }else{
            Log.d(TAG, "doReadyEngine: Engine is ready.")
        }
        selectEngine(type)
        return isEngineReady
    }

}