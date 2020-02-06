package com.example.kodinedependenceyinjection.bike

import android.util.Log
import com.example.kodinedependenceyinjection.bike.component.Engine
import com.example.kodinedependenceyinjection.bike.component.Tool
import com.example.kodinedependenceyinjection.bike.component.Wheel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import kotlin.math.log

class Bike(val engine: Engine, val wheel: Wheel, val tool: Tool) {

    private val TAG: String = Bike::class.java.simpleName

    private var isBikeCheeked: Boolean = false

    /** Engine Data **/
    val petrolEngine: String = "Petrol Engine"
    val dieselEngine: String = "Diesel Engine"
    val electricEngine: String = "Electric Engine"

    /** Wheel Data **/
    val tireTypeFat: String = "Fat Tire"
    val tireTypeTubeLess: String = "Tube Less Tire"
    val tireTypeNormal: String = "Normal Tire"
    val rimTypeStick: String = "5 Stick Rim"
    val rimTypeSpoke: String = "Spoke Rim"
    val rimTypeNormal: String = "Normal Rim"

    internal fun checkBike(){

        isBikeCheeked= true

        engine.doReadyEngine(petrolEngine)

        wheel.doReadyWheel(rimTypeNormal, tireTypeNormal)

        tool.configTool()
    }


    internal fun drive(){
        if(isBikeCheeked){
            Log.d(TAG, "drive: Bike is ready to derived")
            Log.d(TAG, "drive: Bike is derived by ME , .............Bhooooom.............")
            return
        }else{
            this.checkBike()
            this.drive()
        }
    }

}