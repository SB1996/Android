package com.example.kodinedependenceyinjection.bike.component

import android.util.Log
import com.example.kodinedependenceyinjection.bike.subComponent.wheel.Rim
import com.example.kodinedependenceyinjection.bike.subComponent.wheel.Tire

class Wheel(
    val rim: Rim,
    val tire: Tire
) {
    
    private val TAG: String = Wheel::class.java.simpleName

    internal var isWheelReady: Boolean = false

    private lateinit var selectedRim: String
    private lateinit var selectedTire: String


    internal fun selectRim(type: String){
        selectedRim = when(type){

            rim.rimTypeNormal -> rim.rimTypeNormal

            rim.rimTypeSpoke -> rim.rimTypeSpoke

            rim.rimTypeStick -> rim.rimTypeStick
            
            else -> {
                "No Rim Selected"
            }
        }
    }

    internal fun selectTire(type: String){
        selectedTire = when(type){

            tire.tireTypeNormal -> tire.tireTypeNormal

            tire.tireTypeFat -> tire.tireTypeFat

            tire.tireTypeTubeLess -> tire.tireTypeTubeLess

            else -> {
                "No Tire Selected"
            }
        }

    }

    /** Config Wheel Setup **/
    internal fun doReadyWheel(rimType: String, tireType: String) : Boolean{
        if (!isWheelReady){
            Log.d(TAG, "doReadyWheel: Wheel is ready.")
            isWheelReady = true
        }else{
            Log.d(TAG, "doReadyWheel: Wheel is ready.")
        }
        selectRim(rimType)
        selectTire(tireType)
        return isWheelReady
    }
}