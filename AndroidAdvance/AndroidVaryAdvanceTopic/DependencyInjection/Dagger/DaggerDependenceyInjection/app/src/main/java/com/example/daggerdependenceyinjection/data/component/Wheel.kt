package com.example.daggerdependenceyinjection.data.component

import android.util.Log
import com.example.daggerdependenceyinjection.data.component.wheelSubComponent.Rim
import com.example.daggerdependenceyinjection.data.component.wheelSubComponent.Tire
import com.example.daggerdependenceyinjection.di.component.DaggerWheelComponent
import com.example.daggerdependenceyinjection.di.component.WheelComponent
import javax.inject.Inject

class Wheel {

    private val TAG: String = Wheel::class.java.simpleName

    /*** Wheel Component ***/
    private lateinit var wheelComponent: WheelComponent

    /** Rim Data **/
    private val rimType: String = "Default Rim"

    /** Tire Data **/
    private val trieCompanies: String = "MRF Tire"
    private val trieType: String = "Normal Trie"
    private val trieSize: String = "Default Trie"

    /** Wheel Data **/
    var isRimConfigure: Boolean = false
    var isTireConfigure: Boolean = false
    var isWheelConfigure: Boolean = false

    /** Wheel Component Data **/
    @Inject lateinit var rim: Rim
    @Inject lateinit var tire: Tire

    /** Configure Wheel **/
   internal fun wheelConfiguration() : Boolean{

        wheelComponent = DaggerWheelComponent.builder().build()

        wheelComponent.rimInjection(this)
        rim.setRimType(rimType)
        if (!rim.isRimReady){
           rim.doReadyRim()
           isRimConfigure = true
           Log.d(TAG, "wheelConfiguration : Rim Configuration Done")
       }

        wheelComponent.tireInjection(this)
        tire.setTrieType(trieType)
        tire.setTireCompanies(trieCompanies)
        tire.setTrieSize(trieSize)

        if(rim.isRimReady){
           if(!tire.isTrieReady){
               tire.doReadyTrie()
               isTireConfigure = true
               Log.d(TAG, "wheelConfiguration : Tire Configuration Done")
           }
       }

        if(isRimConfigure && isTireConfigure){
            isWheelConfigure = true
            Log.d(TAG, "wheelConfiguration : Wheel Configuration Completed")
        }

        this.wheelInformation()

        return isWheelConfigure
   }


    internal fun wheelInformation(){
        Log.d(TAG, "wheelInformation :: ")
        Log.d(TAG, "wheelInformation : Rim => ${rim.rimType}")
        Log.d(TAG, "wheelInformation : Tire => ${tire.trieType}")
        Log.d(TAG, "wheelInformation : Tire => ${tire.trieSize}")
        Log.d(TAG, "wheelInformation : Tire => ${tire.trieCompanies}")
    }

}