package com.example.networkconnection.broadcast

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.util.Log


@Suppress("DEPRECATION")
class NetworkReceiver : BroadcastReceiver() {
    private val TAG: String = NetworkReceiver::class.java.simpleName

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "NetworkReceiver{} : onReceive() >> " +
            "[line ${Thread.currentThread().stackTrace[2].lineNumber}] :: called"
        )

        val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        val activeNetworks: Array<Network> = connectivityManager.allNetworks
        val extraWifiState = intent.getIntExtra(
            WifiManager.EXTRA_WIFI_STATE,
            WifiManager.WIFI_STATE_UNKNOWN
        )
        when(extraWifiState) {
            WifiManager.WIFI_STATE_DISABLED -> {
                Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 55] :: WIFI STATE DISABLED")
            }
            WifiManager.WIFI_STATE_ENABLED -> {
                Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 58] :: WIFI STATE ENABLED")
            }
            WifiManager.WIFI_STATE_ENABLING -> {
                Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 61] :: WIFI STATE ENABLING")
            }
            WifiManager.WIFI_STATE_DISABLING -> {
                Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 64] :: WIFI STATE DISABLING")
            }
            WifiManager.WIFI_STATE_UNKNOWN -> {
                Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 67] :: WIFI STATE UNKNOWN")
            }
        }
        if (networkInfo != null){
            Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 27] :: Network Information Active[Connect To Internet]")
            if (networkInfo.isAvailable){
                Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 29] :: Network Available")

                val wifiNetworkInfo: NetworkInfo? = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                val mobileNetworkInfo: NetworkInfo? = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                var networkState: NetworkInfo.State?
                if (wifiNetworkInfo != null && wifiNetworkInfo.state.name == "CONNECTED") {
                    Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 35] :: WiFi Network Available / WiFi On")
                    networkState = networkInfo.state

                    val state: String? =  wifiNetworkInfo.state.name
                    val ordinal: Int? =  wifiNetworkInfo.state.ordinal
                    val isAvailable: Boolean? =  wifiNetworkInfo.isAvailable
                    val info: String? =  wifiNetworkInfo.extraInfo
                    val detailedState: String? =  wifiNetworkInfo.detailedState.name
                    val isConnected: Boolean? =  wifiNetworkInfo.isConnected
                    val reason: String? =  wifiNetworkInfo.reason
                    val type: String? =  wifiNetworkInfo.typeName

                }else{
                    Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 46] :: WiFi Off")
                }

                if (mobileNetworkInfo != null && mobileNetworkInfo.state.name == "CONNECTED"){
                    Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 50] :: Mobile Network Available / Mobile Data On")
                    networkState = networkInfo.state

                    val state: String? =  mobileNetworkInfo.state.name
                    val ordinal: Int? =  mobileNetworkInfo.state.ordinal
                    val available: Boolean? =  mobileNetworkInfo.isAvailable
                    val info: String? =  mobileNetworkInfo.extraInfo
                    val detailedState: String? =  mobileNetworkInfo.detailedState.name
                    val isConnected: Boolean? =  mobileNetworkInfo.isConnected
                    val reason: String? =  mobileNetworkInfo.reason
                    val type: String? =  mobileNetworkInfo.typeName

                }else{
                    Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 61] :: Mobile Data Off")
                }
            }else{
                Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 64] :: Network Not Available")
            }
        }else{
            Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 69] :: Network Information NULL[Not Yet Connect To Internet]")
            Log.d(TAG, "NetworkReceiver{} : onReceive() >> [line 70] :: WiFi/Mobile Data May be Off")
        }
    }
}
