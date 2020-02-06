package com.example.daggerdependenceyinjection.data.component.engineSubComponent

import android.util.Log

class DieselEngine() {

    private val TAG: String = DieselEngine::class.java.simpleName

    val engineType: String = "Diesel Engine"
    var isEngineReady: Boolean = false

    internal fun doReadyEngine() : Boolean{
        this.isEngineReady = true
        Log.d(TAG, "Engine(Diesel Engine) ready to use")
        return isEngineReady
    }
}