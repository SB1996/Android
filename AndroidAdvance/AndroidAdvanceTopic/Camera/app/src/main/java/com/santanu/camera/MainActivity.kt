package com.santanu.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    /** Permission List **/
    private val permissionList: Array<String> = arrayOf<String>(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val permissionCode: Int = 10 /** Permission Code **/

    private var camera: Camera? = null
    private var preview: CameraPreview? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        if(reqPermissions(this, permissionList, permissionCode)) {
            camera = this.getCameraInstance()

            if (camera != null) {
                val numberOfCamera: Int = Camera.getNumberOfCameras()
                Log.d(TAG, "MainActivity{} : onCreate() >>" +
                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Number of Camera : $numberOfCamera"
                )
                preview = camera?.let {
                    CameraPreview(this, it)
                }

                preview?.also {
                    val preview: FrameLayout = findViewById(R.id.frame_layout)
                    preview.addView(it)
                }

            }
        }


    }

    /** Group permission request **/
    private fun reqPermissions(context: Context, permissionsList: Array<String>, permissionReqCode: Int) : Boolean{

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "MainActivity{} : onRequestPermissionsResult() >>" +
            " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Called"
        )

        when(requestCode) {
            permissionCode -> {
                Log.d(TAG, "MainActivity{} : onRequestPermissionsResult() >>" +
                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: permission are granted"
                )
            }

            else -> {
                Log.d(TAG, "MainActivity{} : onRequestPermissionsResult() >>" +
                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: permission are not granted"
                )
            }
        }
    }

    /** Check if this device has a camera **/
    private fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }

    fun getCameraInstance(): Camera? {
        return try {
            Camera.open() // attempt to get a Camera instance
        } catch (e: Exception) {
            // Camera is not available (in use or does not exist)
            null // returns null if camera is unavailable
        }
    }

}
