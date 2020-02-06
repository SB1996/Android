package com.example.ftpserver.permission

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permission(private val context: Context) {
    
    private val TAG: String = Permission::class.java.simpleName
    
    private var permissionList: PermissionList = PermissionList()

    /** Single permission request **/
    internal fun requestSinglePermission(permissionName: String, permissionCode: Int) : Boolean {

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
    internal fun requestGroupPermission(permissionsList: Array<String>, permissionCode: Int) : Boolean{

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
        val wifiPermissionList = permissionList.wifiPermissionList
        
        var permissionDueList: Array<String> = arrayOf<String>()
        
        for(permission: String in wifiPermissionList){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.isNotEmpty()) {
            Log.d(TAG, "Permissions{} : requestForWiFiPermissions() >> [line 105] :: permissions have to requested")
            ActivityCompat.requestPermissions(context as Activity, permissionDueList, permissionList.wifiPermissionListCode)
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

    /** request for Bluetooth permission**/
    internal fun requestForBluetoothPermissions() : Boolean{

        var isBluetoothPermissionGranted: Boolean = false

        var permissionDueList: Array<String> = arrayOf<String>()

        val bluetoothPermissionList = permissionList.bluetoothPermissionList
        for(permission: String in bluetoothPermissionList){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.isNotEmpty()) {
            Log.d(TAG, "Permissions{} : requestForBluetoothPermissions() >> [line 105] :: permissions have to requested")
            ActivityCompat.requestPermissions(context as Activity, permissionDueList, permissionList.bluetoothPermissionListCode)
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

    /** request for Network permission**/
    internal fun requestForNetworkPermissions() : Boolean{

        var isNetworkPermissionGranted: Boolean = false
        val networkPermissionList = permissionList.networkPermissionList

        var permissionDueList: Array<String> = arrayOf<String>()

        for(permission: String in networkPermissionList){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.isNotEmpty()) {
            Log.d(TAG, "Permissions{} : requestForNetworkPermissions() >> [line 145] :: permissions have to requested")
            ActivityCompat.requestPermissions(context as Activity, permissionDueList, permissionList.networkPermissionListCode)
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

    /** request for Storage permission**/
    internal fun requestForStoragePermissions() : Boolean{

        var isStoragePermissionGranted: Boolean = false
        val storagePermissionList = permissionList.storagePermissionList

        var permissionDueList: Array<String> = arrayOf<String>()

        for(permission: String in storagePermissionList){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.isNotEmpty()) {
            Log.d(TAG, "Permissions{} : requestForStoragePermissions() >> [line 145] :: permissions have to requested")
            ActivityCompat.requestPermissions(context as Activity, permissionDueList, permissionList.storagePermissionListCode)
            for(permission: String in storagePermissionList){
                if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false
                }
            }
        }else {
            Log.d(TAG,"Permissions{} : requestForStoragePermissions() >> [line 153] :: all Storage permission are granted")
            isStoragePermissionGranted = true
        }

        return isStoragePermissionGranted
    }

    /** request for permission**/
    internal fun requestForAllPermissions() : Boolean{

        var isAllPermissionGranted: Boolean = false
        val allPermissionList = permissionList.allPermissionList

        var permissionDueList: Array<String> = arrayOf<String>()

        for(permission: String in allPermissionList){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.isNotEmpty()) {
            Log.d(TAG, "Permissions{} : requestForAllPermissions() >> [line 145] :: permissions have to requested")
            ActivityCompat.requestPermissions(context as Activity, permissionDueList, permissionList.allPermissionListCode)
            for(permission: String in allPermissionList){
                if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false
                }
            }
        }else {
            Log.d(TAG,"Permissions{} : requestForAllPermissions() >> [line 153] :: all All permission are granted")
            isAllPermissionGranted = true
        }

        return isAllPermissionGranted
    }

}