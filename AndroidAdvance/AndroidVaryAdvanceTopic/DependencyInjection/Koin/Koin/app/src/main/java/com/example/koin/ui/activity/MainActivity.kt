package com.example.koin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.koin.R
import com.example.koin.module.applicationModule
import com.example.koin.rc.Reference
import com.example.koin.rc.Reference00
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName
    private val reference: Reference by inject()
    private val reference00: Reference00 by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            androidFileProperties()
            modules(listOf(applicationModule))
        }

        Log.d(TAG, "onCreate[reference]: ${reference.data}")
        Log.d(TAG, "onCreate[reference00]: ${reference00.data}")
    }
}
