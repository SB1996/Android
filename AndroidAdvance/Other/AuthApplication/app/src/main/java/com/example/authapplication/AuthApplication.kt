package com.example.authapplication

import android.util.Log
import com.example.authapplication.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject
import javax.inject.Named

class AuthApplication : DaggerApplication() {

    private val TAG: String by lazy { AuthApplication::class.java.simpleName }

    @field:[Inject]
    lateinit var massage: String

    @field:[Inject Named("SuccessMassage")]
    lateinit var successMassage: String

    @field:[Inject Named("FailedMassage")]
    lateinit var failedMassage: String

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().application(this).build()
    }


    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate: massage :> $massage")
        Log.d(TAG, "onCreate: successMassage :> $successMassage")
        Log.d(TAG, "onCreate: failedMassage :> $failedMassage")

    }

}