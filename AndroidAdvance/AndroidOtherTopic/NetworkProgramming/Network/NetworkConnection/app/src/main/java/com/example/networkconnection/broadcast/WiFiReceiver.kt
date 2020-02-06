package com.example.networkconnection.broadcast

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log


@Suppress("DEPRECATION")
class WiFiReceiver : BroadcastReceiver() {
    private val TAG: String = WiFiReceiver::class.java.simpleName

    @SuppressLint("HardwareIds")
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "WiFiReceiver{} : onReceive() >> [line 11] :: Called")

        val action: String? = intent.action
        val networkInfo: NetworkInfo? = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO)

        if (networkInfo != null ){
            Log.d(TAG, "WiFiReceiver{} : onReceive() >> [line 25] :: WiFi is Turn On")
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)

            if (networkInfo.isAvailable){
                Log.d(TAG, "WiFiReceiver{} : onReceive() >> [line 35] :: WiFi Available")
            }
            if (networkInfo.isConnectedOrConnecting){
                Log.d(TAG, "WiFiReceiver{} : onReceive() >> [line 35] :: WiFi Connecting or Connected")
            }
            if (networkInfo.isConnected){
                Log.d(TAG, "WiFiReceiver{} : onReceive() >> [line 28] :: WiFi Connected")
                val wifiInfo: WifiInfo = wifiManager.connectionInfo

                val wifiSSID: String? = wifiInfo.ssid
                val wifiBSSID: String? = wifiInfo.bssid
                val wifiMacAddress: String? = wifiInfo.macAddress
                Log.d(TAG, "WiFiReceiver{} : onReceive() >> [line 39] :: WiFi SSID : $wifiSSID")
                Log.d(TAG, "WiFiReceiver{} : onReceive() >> [line 40] :: WiFi BSSID : $wifiBSSID")
                Log.d(TAG, "WiFiReceiver{} : onReceive() >> [line 41] :: WiFi MAC Address : $wifiMacAddress")
                Log.d(TAG, "WiFiReceiver{} : onReceive() >> [line 42] :: Roaming : ${networkInfo.isRoaming}")
            }
            if (!networkInfo.isConnectedOrConnecting){
                Log.d(TAG, "WiFiReceiver{} : onReceive() >> [line 35] :: WiFi Disconnecting and Disconnected")
            }
            if (networkInfo.isFailover){
                Log.d(TAG, "WiFiReceiver{} : onReceive() >> [line 35] :: Error : WiFi Connecting Failed")
            }
        }else{
            Log.d(TAG, "WiFiReceiver{} : onReceive() >> [line 47] :: WiFi is Turn Off")
        }
    }
}