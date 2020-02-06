package com.example.daggerdependenceyinjection.data.component.engineSubComponent

import android.util.Log

class ElectricEngine {

    private val TAG: String = ElectricEngine::class.java.simpleName

    val engineType: String = "Electric Engine"
    var isEngineReady: Boolean = false

    internal fun doReadyEngine() : Boolean{
        this.isEngineReady = true
        Log.d(TAG, "Engine(Electric Engine) ready to use")
        return isEngineReady
    }
}