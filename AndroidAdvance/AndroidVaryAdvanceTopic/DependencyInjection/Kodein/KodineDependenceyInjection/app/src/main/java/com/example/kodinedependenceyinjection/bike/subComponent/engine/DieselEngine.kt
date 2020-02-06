package com.example.kodinedependenceyinjection.bike.subComponent.engine

import android.util.Log

class DieselEngine {
    
    private val TAG: String = DieselEngine::class.java.simpleName

    val name: String = "Diesel Engine"
    internal var isEngineReady: Boolean = false

    /** Config Engine Setup **/
    internal fun doReadyEngine() : Boolean{
        if (!isEngineReady){
            Log.d(TAG, "doReadyEngine: Diesel Engine is ready.")
            isEngineReady = true
        }else{
            Log.d(TAG, "doReadyEngine: Diesel Engine is ready.")
        }

        return isEngineReady
    }
}