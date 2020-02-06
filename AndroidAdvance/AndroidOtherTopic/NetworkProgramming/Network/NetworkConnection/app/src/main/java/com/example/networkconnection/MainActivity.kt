package com.example.networkconnection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.networkconnection.Permission.Permissions
import com.example.networkconnection.network.NetworkDetails

class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    /** permission request instance **/
    private lateinit var permissions: Permissions
    private var isNetworkPermissionGranted: Boolean = false

    private lateinit var networkDetails: NetworkDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initialized() /** to initialized needed thing **/
        if (!isNetworkPermissionGranted){
            isNetworkPermissionGranted = permissions.requestForNetworkPermissions()
            Log.d(TAG, "MainActivity{} : onCreate() >> [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Network Permission : $isNetworkPermissionGranted")
        }

        Log.d(TAG, "MainActivity{} : onCreate() >> [line 28] :: IP Address : ${networkDetails.getIPAddress(true)}")
        Log.d(TAG, "MainActivity{} : onCreate() >> [line 28] :: MAC Address : ${networkDetails.getMACAddress("wlan0")}")

        
        Log.d(TAG, "${this.localClassName}{} : ${this.localClassName}() >> ${this.localClassName} :: ")
    }

    private fun initialized() {
        Log.d(TAG, "MainActivity{} : initialized() >> [line 21] :: Called")

        permissions = Permissions(this)

        networkDetails = NetworkDetails.build()
    }
}
