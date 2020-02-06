package com.example.kodinedependenceyinjection.bike.subComponent.wheel

import android.util.Log

class Rim {

    private val TAG: String = Rim::class.java.simpleName

    val rimTypeStick: String = "5 Stick Rim"
    val rimTypeSpoke: String = "Spoke Rim"
    val rimTypeNormal: String = "Normal Rim"

    internal var isRimReady: Boolean = false

    /** Config Rim Setup **/
    internal fun doReadyRim() : Boolean{
        if (!isRimReady){
            Log.d(TAG, "doReadyRim: Rim is ready.")
            isRimReady = true
        }else{
            Log.d(TAG, "doReadyRim: Rim is ready.")
        }

        return isRimReady
    }
}