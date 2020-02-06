package com.example.daggerdi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.daggerdi.data.Connection
import com.example.daggerdi.data.RealConnection
import com.example.daggerdi.di.DaggerMainActivityComponent
import com.example.daggerdi.di.MainActivityComponent
import com.example.daggerdi.network.NetworkRequest
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    lateinit var mainActivityComponent: MainActivityComponent

    @Inject lateinit var connection: Connection
    @Inject lateinit var networkRequest: NetworkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityComponent = DaggerMainActivityComponent.builder().build()
        mainActivityComponent.injectMainActivityField(this)




        Log.d(TAG, "MainActivity{} : onCreate() >>" +
            " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: is Connection Successful : ${connection.makeRequest()}"
        )

        Log.d(TAG, "MainActivity{} : onCreate() >>" +
            " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Host Address : ${networkRequest.sendRequest()}"
        )
    }
}
