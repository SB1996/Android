package com.example.kodinedependenceyinjection.bike.subComponent.engine

import android.util.Log

class PetrolEngine {

    private val TAG: String = PetrolEngine::class.java.simpleName

    val name: String = "Petrol Engine"
    internal var isEngineReady: Boolean = false

    /** Config Engine Setup **/
    internal fun doReadyEngine() : Boolean{
        if (!isEngineReady){
            Log.d(TAG, "doReadyEngine: Petrol Engine is ready.")
            isEngineReady = true
        }else{
            Log.d(TAG, "doReadyEngine: Petrol Engine is ready.")
        }

        return isEngineReady
    }
}