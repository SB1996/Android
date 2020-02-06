package com.example.kodinedependenceyinjection.bike.subComponent.tools

import android.util.Log

class Tools {

    private val TAG: String = Tools::class.java.simpleName

    internal var isAllToolReady: Boolean = false
    internal var isBreakReady: Boolean = false
    internal var isAcceleratorReady: Boolean = false
    internal var isClutchReady: Boolean = false
    internal var isLightSystemReady: Boolean = false


    /** Config Tools Setup **/
    fun doReadyAllTools() : Boolean{
        if (!isAllToolReady){

            if (!isClutchReady) doReadyClutch()
            if (!isAcceleratorReady) doReadyAccelerator()
            if (!isBreakReady) doReadyBreak()
            if (!isLightSystemReady) doReadyLightSystem()

            Log.d(TAG, "doReadyEngine: All Tools is ready.")

            if (isAcceleratorReady && isBreakReady && isClutchReady && isLightSystemReady){

                isAllToolReady = true
            }
        }else{
            Log.d(TAG, "doReadyEngine: All Tools is ready.")
        }

        return isAllToolReady
    }

    /** Config Break Setup **/
    internal fun doReadyBreak() : Boolean{
        if (!isBreakReady){
            Log.d(TAG, "doReadyEngine: Break is ready.")
            isBreakReady = true
        }else{
            Log.d(TAG, "doReadyEngine: Break is ready.")
        }

        return isBreakReady
    }

    /** Config Clutch Setup **/
    internal fun doReadyClutch() : Boolean{
        if (!isClutchReady){
            Log.d(TAG, "doReadyEngine: Clutch is ready.")
            isClutchReady = true
        }else{
            Log.d(TAG, "doReadyEngine: Clutch is ready.")
        }

        return isClutchReady
    }

    /** Config Accelerator Setup **/
    internal fun doReadyAccelerator() : Boolean{
        if (!isAcceleratorReady){
            Log.d(TAG, "doReadyEngine: Accelerator is ready.")
            isAcceleratorReady = true
        }else{
            Log.d(TAG, "doReadyEngine: Accelerator is ready.")
        }

        return isAcceleratorReady
    }

    /** Config Light System Setup **/
    internal fun doReadyLightSystem() : Boolean{
        if (!isLightSystemReady){
            Log.d(TAG, "doReadyEngine: Light System is ready.")
            isLightSystemReady = true
        }else{
            Log.d(TAG, "doReadyEngine: Light System is ready.")
        }

        return isLightSystemReady
    }
}