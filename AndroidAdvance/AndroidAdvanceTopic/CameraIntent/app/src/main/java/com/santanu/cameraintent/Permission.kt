package com.santanu.cameraintent

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permission {
    private val TAG: String = Permission::class.java.simpleName



    /** Group permission request **/
    internal fun requestPermissions(context: Context, permissionsList: Array<String>, permissionReqCode: Int) : Boolean {

        var isAllPermissionGranted: Boolean = false

        val permissionNeededList: Array<String> = permissionsList
        var permissionDueList: Array<String> = arrayOf<String>()

        for(permission: String in permissionNeededList){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.isNotEmpty()) {
            Log.d(TAG, "MainActivity{} : reqPermissions() >>" +
                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: permissions have to requested"
            )
            ActivityCompat.requestPermissions(context as Activity, permissionDueList, permissionReqCode)
            for(permission: String in permissionNeededList){
                if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false
                }
            }
        }else{
            Log.d(TAG, "MainActivity{} : reqPermissions() >>" +
                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: all permission are granted"
            )
            isAllPermissionGranted = true
        }

        return isAllPermissionGranted
    }
}