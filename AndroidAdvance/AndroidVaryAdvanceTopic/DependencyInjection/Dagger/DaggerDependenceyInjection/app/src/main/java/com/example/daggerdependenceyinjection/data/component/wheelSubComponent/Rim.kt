package com.example.daggerdependenceyinjection.data.component.wheelSubComponent

class Rim {

    private val TAG: String = Rim::class.java.simpleName

    var rimType: String = "Default Rim"
    var isRimReady: Boolean = false

    internal fun setRimType(size: String){
        rimType = when(size) {
            "Small Rim" -> { "Small Rim" }
            "Normal Rim" -> { "Normal Rim" }
            "Big Rim" -> { "Big Rim" }
            "Very Big Rim" -> { "Very Big Rim" }
            else -> { "Default Rim" }
        }
    }

    internal fun doReadyRim() : Boolean{
        this.isRimReady = true

        return isRimReady
    }
}