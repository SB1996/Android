package com.example.mvvmarchitecture

import android.app.Application

class MainApplication : Application() {

    companion object{
        internal val ApplicationTAG: String = MainApplication::class.java.simpleName
    }

}