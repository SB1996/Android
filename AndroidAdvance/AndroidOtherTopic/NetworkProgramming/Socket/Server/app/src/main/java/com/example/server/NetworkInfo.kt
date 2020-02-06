package com.example.server

import android.content.Context
import android.net.wifi.WifiManager

class NetworkInfo(private val context: Context) {
    private val TAG: String = NetworkInfo::class.java.simpleName

    internal fun getWifiNetworkIP(): String? {
        val mWifiManager: WifiManager? = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (mWifiManager != null && mWifiManager.isWifiEnabled) {
            val ip = mWifiManager.connectionInfo.ipAddress
            return ((ip and 0xFF).toString() + "." + (ip shr 8 and 0xFF) + "." + (ip shr 16 and 0xFF) + "." + (ip shr 24 and 0xFF))
        }
        return null
    }
}