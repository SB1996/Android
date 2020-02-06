package com.example.wifi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.wifi.permission.Permissions
import com.example.wifi.wifiutils.WiFiNetworkUtils

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var wifiStatus: TextView

    private lateinit var permissions: Permissions
    private var isWiFiPermissionGranted: Boolean = false

    private lateinit var wifiNetworkUtils: WiFiNetworkUtils
    private var isWiFiConnected: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wifiStatus = findViewById(R.id.tvWifiStatus)

        permissions = Permissions(this@MainActivity as Context)
        isWiFiPermissionGranted = permissions.requestForWiFiPermissions()
        Log.d(TAG, "MainActivity{} : onCreate() >> [line 23] :: isWiFiPermissionGranted : $isWiFiPermissionGranted")

        wifiNetworkUtils = WiFiNetworkUtils(this)
        isWiFiConnected = wifiNetworkUtils.isWiFiConnected()

        Log.d(TAG, "MainActivity{} : onCreate() >> [line 39] :: $isWiFiConnected")

    }




}
