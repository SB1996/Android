package com.example.kodinedependenceyinjection.bike.subComponent.engine

import android.util.Log

class ElectricEngine {
    
    private val TAG: String = ElectricEngine::class.java.simpleName

    val name: String = "Electric Engine"
    internal var isEngineReady: Boolean = false

    /** Config Engine Setup **/
    internal fun doReadyEngine() : Boolean{
        if (!isEngineReady){
            Log.d(TAG, "doReadyEngine: Electric Engine is ready.")
            isEngineReady = true
        }else{
            Log.d(TAG, "doReadyEngine: Electric Engine is ready.")
        }

        return isEngineReady
    }
}