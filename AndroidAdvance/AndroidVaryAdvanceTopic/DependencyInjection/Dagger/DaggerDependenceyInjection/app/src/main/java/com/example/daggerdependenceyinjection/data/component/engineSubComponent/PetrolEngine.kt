package com.example.daggerdependenceyinjection.data.component.engineSubComponent

import android.util.Log

class PetrolEngine {

    private val TAG: String = PetrolEngine::class.java.simpleName

    val engineType: String = "Petrol Engine"
    var isEngineReady: Boolean = false

    internal fun doReadyEngine() : Boolean{
        this.isEngineReady = true
        Log.d(TAG, "Engine(Petrol Engine) ready to use")
        return isEngineReady
    }
}