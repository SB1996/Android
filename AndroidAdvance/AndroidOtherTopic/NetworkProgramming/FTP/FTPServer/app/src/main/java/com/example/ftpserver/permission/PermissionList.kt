package com.example.ftpserver.permission

import android.Manifest
import android.content.Context

class PermissionList {
    internal val TAG: String = PermissionList::class.java.simpleName


    /** All Permission List **/
    internal val allPermissionList: Array<String> = arrayOf<String>(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.CHANGE_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    internal val allPermissionListCode: Int = 1 /** All Permission Code **/

    /** Network Permission List **/
    internal val networkPermissionList: Array<String> = arrayOf<String>(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.CHANGE_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_MULTICAST_STATE
    )
    internal val networkPermissionListCode: Int = 10 /** Network Permission Code **/

    /** WiFi Permission List **/
    internal val wifiPermissionList: Array<String> = arrayOf<String>(
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_MULTICAST_STATE
    )
    internal val wifiPermissionListCode: Int = 20 /** WiFi Permission Code **/

    /** Bluetooth Permission List **/
    internal val bluetoothPermissionList: Array<String> = arrayOf<String>(
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_ADMIN
    )
    internal val bluetoothPermissionListCode: Int = 30 /** Bluetooth Permission Code **/


    /** Storage Permission List **/
    internal val storagePermissionList: Array<String> = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    internal val storagePermissionListCode: Int = 40 /** Storage Permission Code **/
}