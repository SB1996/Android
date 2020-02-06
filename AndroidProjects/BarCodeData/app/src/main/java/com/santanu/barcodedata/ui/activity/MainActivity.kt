package com.santanu.barcodedata.ui.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.santanu.barcodedata.R
import com.santanu.barcodedata.di.DaggerMainActivityComponent
import com.santanu.barcodedata.di.MainActivityComponent
import com.santanu.barcodedata.di.module.PermissionModule
import com.santanu.barcodedata.permission.Permissions
import com.santanu.barcodedata.ui.fragment.MainFragmentDirections
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    /** view widget instance **/
    private lateinit var scanBtn: MaterialButton
    private lateinit var selectPicBtn: MaterialButton

    private lateinit var navController: NavController

    /** Permission List **/
    private val permissionList: Array<String> = arrayOf<String>(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val permissionCode: Int = 12345 /** Permission Code **/

    private lateinit var mainActivityComponent: MainActivityComponent

    @Inject lateinit var permission: Permissions

    private fun initializedView() {
        scanBtn = findViewById(R.id.m_btn_scan)
        selectPicBtn = findViewById(R.id.m_btn_pic)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializedView()

        mainActivityComponent = DaggerMainActivityComponent.builder().permissionModule(
            PermissionModule(this)
        ).build()
        mainActivityComponent.injectMainActivityField(this@MainActivity)

        permission.requestForPermissions(permissionList, permissionCode)

        scanBtn.setOnClickListener {
            Log.d(TAG, "MainActivity{} : onCreate() >>" +
                " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Called"
            )
            navController = Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
            val navDirections : NavDirections = MainFragmentDirections.actionMainFragmentToCodeScanFragment()
            navController.navigate(navDirections)
        }


    }

    /** Check if this device has a camera **/
    private fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "MainActivity{} : onRequestPermissionsResult() >>" +
                " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Called"
        )

        when (requestCode) {
            permissionCode -> {
                Log.d(TAG, "MainActivity{} : onRequestPermissionsResult() >>" +
                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Permission Granted"
                )
            }

            else -> {
                Log.d(TAG, "MainActivity{} : onRequestPermissionsResult() >>" +
                        " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Permission Denial"
                )
            }
        }
    }


}
