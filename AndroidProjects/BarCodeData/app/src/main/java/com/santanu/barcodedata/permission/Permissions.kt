package com.santanu.barcodedata.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permissions(private val context: Context) {

    private val TAG: String = Permissions::class.java.simpleName


    /** Network Permission List **/
    private val networkPermissionList: Array<String> = arrayOf<String>(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.CHANGE_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_MULTICAST_STATE
    )
    private val networkPermissionListCode: Int = 10 /** Network Permission Code **/

    /** WiFi Permission List **/
    private val wifiPermissionList: Array<String> = arrayOf<String>(
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_MULTICAST_STATE
    )
    private val wifiPermissionListCode: Int = 20 /** WiFi Permission Code **/

    /** Bluetooth Permission List **/
    private val bluetoothPermissionList: Array<String> = arrayOf<String>(
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_ADMIN
    )
    private val bluetoothPermissionListCode: Int = 30 /** Bluetooth Permission Code **/

    /** Storage Permission List **/
    private val storagePermissionList: Array<String> = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val storagePermissionListCode: Int = 40 /** Storage Permission Code **/

    /** Permission List **/
    private val permissionList: Array<String> = arrayOf<String>(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val permissionListCode: Int = 12345 /** Permission Code **/

    /** Single permission request **/
    internal fun requestForPermission(permissionName: String, permissionCode: Int) : Boolean {

        var isAllPermissionGranted: Boolean = false

        if (ContextCompat.checkSelfPermission(context as Activity, permissionName) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permissions{} : requestSinglePermission() >> [line 53] :: permission is granted")
            isAllPermissionGranted = true
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissionName)) {
                AlertDialog.Builder(context)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for access")
                    .setPositiveButton("Allow", DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(context, arrayOf(permissionName), permissionCode)
                        isAllPermissionGranted = ContextCompat.checkSelfPermission(context, permissionName) == PackageManager.PERMISSION_GRANTED
                    })
                    .setNegativeButton("Decline", DialogInterface.OnClickListener { dialog, which ->
                        isAllPermissionGranted = false
                        dialog.dismiss()
                    })
                    .setNeutralButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                        isAllPermissionGranted = false
                        dialog.dismiss()
                    })
                    .create().show()
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(permissionName), permissionCode)
            }
        }

        return isAllPermissionGranted
    }

    /** Group permission request **/
    internal fun requestForPermissions(permissionsList: Array<String>, permissionCode: Int) : Boolean{

        var isAllPermissionGranted: Boolean = false

        val permissionNeededList: Array<String> = permissionsList
        var permissionDueList: Array<String> = arrayOf<String>()

        for(permission: String in permissionNeededList){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.isNotEmpty()) {
            Log.d(TAG, "Permissions{} : requestGroupPermission() >> [line 82] :: permissions have to requested")
            ActivityCompat.requestPermissions(context as Activity, permissionDueList, permissionCode)
            for(permission: String in permissionNeededList){
                if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                   return false
                }
            }
        }else{
            Log.d(TAG, "Permissions{} : requestGroupPermission() >> [line 84] :: all permission are granted")
            isAllPermissionGranted = true
        }

        return isAllPermissionGranted
    }

    /** request for WiFi permission**/
    internal fun requestForWiFiPermissions() : Boolean{

        var isWiFIPermissionGranted: Boolean = false

        var permissionDueList: Array<String> = arrayOf<String>()

        for(permission: String in wifiPermissionList){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.isNotEmpty()) {
            Log.d(TAG, "Permissions{} : requestForWiFiPermissions() >> [line 105] :: permissions have to requested")
            ActivityCompat.requestPermissions(context as Activity, permissionDueList, wifiPermissionListCode)
            for(permission: String in wifiPermissionList){
                if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false
                }
            }
        }else {
            Log.d(TAG,"Permissions{} : requestForWiFiPermissions() >> [line 113] :: all WiFi permission are granted")
            isWiFIPermissionGranted = true
        }

        return isWiFIPermissionGranted
    }

    /** request for Bluetooth permission **/
    internal fun requestForBluetoothPermissions() : Boolean{

        var isBluetoothPermissionGranted: Boolean = false

        var permissionDueList: Array<String> = arrayOf<String>()

        for(permission: String in bluetoothPermissionList){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.isNotEmpty()) {
            Log.d(TAG, "Permissions{} : requestForBluetoothPermissions() >> [line 105] :: permissions have to requested")
            ActivityCompat.requestPermissions(context as Activity, permissionDueList, bluetoothPermissionListCode)
            for(permission: String in bluetoothPermissionList){
                if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false
                }
            }
        }else {
            Log.d(TAG,"Permissions{} : requestForBluetoothPermissions() >> [line 113] :: all WiFi permission are granted")
            isBluetoothPermissionGranted = true
        }

        return isBluetoothPermissionGranted
    }

    /** request for Network permission **/
    internal fun requestForNetworkPermissions() : Boolean{

        var isNetworkPermissionGranted: Boolean = false

        var permissionDueList: Array<String> = arrayOf<String>()

        for(permission: String in networkPermissionList){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.isNotEmpty()) {
            Log.d(TAG, "Permissions{} : requestForNetworkPermissions() >> [line 145] :: permissions have to requested")
            ActivityCompat.requestPermissions(context as Activity, permissionDueList, wifiPermissionListCode)
            for(permission: String in networkPermissionList){
                if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false
                }
            }
        }else {
            Log.d(TAG,"Permissions{} : requestForNetworkPermissions() >> [line 153] :: all Network permission are granted")
            isNetworkPermissionGranted = true
        }

        return isNetworkPermissionGranted
    }

    /** request for Network permission **/
    internal fun requestForStoragePermissions() : Boolean{

        var isStoragePermissionGranted: Boolean = false

        var permissionDueList: Array<String> = arrayOf<String>()

        for(permission: String in storagePermissionList){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.isNotEmpty()) {
            Log.d(TAG, "Permissions{} : requestForStoragePermissions() >> [line 145] :: permissions have to requested")
            ActivityCompat.requestPermissions(context as Activity, permissionDueList, storagePermissionListCode)
            for(permission: String in storagePermissionList){
                if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false
                }
            }
        }else {
            Log.d(TAG,"Permissions{} : requestForStoragePermissions() >> [line 153] :: all Network permission are granted")
            isStoragePermissionGranted = true
        }

        return isStoragePermissionGranted
    }

    /** request for permission **/
    internal fun requestNeededPermissions() : Boolean{

        var isPermissionGranted: Boolean = false

        var permissionDueList: Array<String> = arrayOf<String>()

        for(permission: String in permissionList){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.isNotEmpty()) {
            Log.d(TAG, "Permissions{} : requestForPermissions() >> [line 145] :: permissions have to requested")
            ActivityCompat.requestPermissions(context as Activity, permissionDueList, permissionListCode)
            for(permission: String in permissionList){
                if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false
                }
            }
        }else {
            Log.d(TAG,"Permissions{} : requestForPermissions() >> [line 153] :: all permission are granted")
            isPermissionGranted = true
        }

        return isPermissionGranted
    }

}