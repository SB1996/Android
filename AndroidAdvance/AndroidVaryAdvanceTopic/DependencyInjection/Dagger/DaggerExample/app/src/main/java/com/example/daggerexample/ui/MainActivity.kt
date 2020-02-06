package com.example.daggerexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.daggerexample.R
import com.example.daggerexample.di.component.DaggerMainComponent
import com.example.daggerexample.di.component.MainComponent
import com.example.daggerexample.di.module.MainModule
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName

    /*** Component ***/
    private lateinit var component: MainComponent

    @field:[Inject Named("massage")] lateinit var massage: String
    @field:[Inject Named("anotherMassage")] lateinit var anotherMassage: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component = DaggerMainComponent.builder().mainModule(MainModule(this)).build()
        component.injectMainActivity(this@MainActivity)

        Log.d(TAG, "onCreate:: Massage is initiated : $massage")
        Log.d(TAG, "onCreate:: Massage is initiated : $anotherMassage")
    }
}
