package com.example.kodinedependenceyinjection.bike.subComponent.wheel

import android.util.Log

class Tire {

    private val TAG: String = Tire::class.java.simpleName

    val tireTypeFat: String = "Fat Tire"
    val tireTypeTubeLess: String = "Tube Less Tire"
    val tireTypeNormal: String = "Normal Tire"

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