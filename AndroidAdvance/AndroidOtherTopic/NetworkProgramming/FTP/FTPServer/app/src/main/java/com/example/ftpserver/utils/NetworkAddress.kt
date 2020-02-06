package com.example.ftpserver.utils

import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import com.example.ftpserver.utils.ftp.FTPServerUtils
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class NetworkAddress(private val context: Context) {
    private val TAG: String = NetworkAddress::class.java.simpleName


    private fun wifiIpAddress(ftpServerUtils: FTPServerUtils) : String?{
        try {
            if (wifiHotspotEnabled(context)) {
                return "192.168.43.1"
            }
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return ftpServerUtils.getIPAddress(true)
    }

    @Throws(InvocationTargetException::class, IllegalAccessException::class)
    private fun wifiHotspotEnabled(context : Context) : Boolean {
        val manager: WifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val method: Method
        try {
            method = manager.javaClass.getDeclaredMethod("isWifiApEnabled")
            method.isAccessible = true
            return method.invoke(manager) as Boolean
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }

        return false
    }

    private fun checkWifiOnAndConnected(context: Context)  : Boolean {

        val wifiMgr: WifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (wifiMgr.isWifiEnabled) {
            val wifiInfo: WifiInfo = wifiMgr.connectionInfo
            return wifiInfo.networkId != -1
        }
        else {
            return false
        }
    }
}