package com.example.wifi.permission

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


    /** WiFi Permission List **/
    private val wifiPermissionList: Array<String> = arrayOf<String>(
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_MULTICAST_STATE
    )
    /** WiFi Permission Code **/
    private val wifiPermissionListCode: Int = 10

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

}