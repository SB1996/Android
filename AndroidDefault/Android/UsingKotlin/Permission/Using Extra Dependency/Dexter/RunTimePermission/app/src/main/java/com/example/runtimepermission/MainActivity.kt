package com.example.runtimepermission

import android.Manifest
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.*
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import android.content.Intent
import android.net.Uri
import android.provider.Settings


class MainActivity : AppCompatActivity() {

    private lateinit var singlePermissionBtn: Button
    private lateinit var groupPermissionBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        singlePermissionBtn = findViewById(R.id.btn_single)
        groupPermissionBtn = findViewById(R.id.btn_group)

        singlePermissionBtn.setOnClickListener {

            runTimePermission()
        }

        groupPermissionBtn.setOnClickListener {

            runTimePermissions()
        }
    }

    //runtime permissions for single permission
    fun runTimePermission(){
        Dexter.withActivity(this@MainActivity)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object: PermissionListener{
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(this@MainActivity, "Permission Denied", Toast.LENGTH_SHORT).show()
                    if(response!!.isPermanentlyDenied){
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("Runtime Permission")
                            .setMessage("Setting > Application > permissions")
                            .setPositiveButton("Done", DialogInterface.OnClickListener { dialog, id ->
                                dialog.dismiss()
                                val appSettings = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
                                appSettings.addCategory(Intent.CATEGORY_DEFAULT)
                                appSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(appSettings)
                            })
                            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                                dialog.dismiss()

                                AlertDialog.Builder(this@MainActivity)
                                    .setTitle("Runtime Permission")
                                    .setMessage("Have to Grant this Permission manually to running application")
                                    .setPositiveButton("Done", DialogInterface.OnClickListener { dialog, id ->
                                        dialog.dismiss()
                                    }).show()
                            }).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Runtime Permission")
                        .setMessage("Have to accept the Permission to running application")
                        .setPositiveButton("Done", DialogInterface.OnClickListener { dialog, id ->
                            dialog.dismiss()
                            token?.continuePermissionRequest()
                        })
                        .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                            dialog.dismiss()
                            token?.cancelPermissionRequest()
                        }).show()
                }

            })
            .withErrorListener(object: PermissionRequestErrorListener{
                override fun onError(error: DexterError?) {
                    Toast.makeText(this@MainActivity, "Error Occurred While Granting Permission", Toast.LENGTH_SHORT).show()
                }

            })
            .onSameThread()
        .check()

    }

    //runtime permissions for group permission
    fun runTimePermissions(){
        Dexter.withActivity(this@MainActivity)
            .withPermissions(
                Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
            )
            .withListener(object: MultiplePermissionsListener {

                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        Toast.makeText(this@MainActivity, "All permission Granted", Toast.LENGTH_SHORT).show()
                    }else{
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            AlertDialog.Builder(this@MainActivity)
                                .setTitle("Runtime Permission")
                                .setMessage("Setting > Application > permissions")
                                .setPositiveButton("Done", DialogInterface.OnClickListener { dialog, id ->
                                    dialog.dismiss()
                                    val appSettings = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
                                    appSettings.addCategory(Intent.CATEGORY_DEFAULT)
                                    appSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(appSettings)
                                })
                                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                                    dialog.dismiss()

                                    AlertDialog.Builder(this@MainActivity)
                                        .setTitle("Runtime Permission")
                                        .setMessage("Have to Grant this Permission manually to running application")
                                        .setPositiveButton("Done", DialogInterface.OnClickListener { dialog, id ->
                                            dialog.dismiss()
                                        }).show()
                                }).show()
                        }
                    }

                }

                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?,token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }

            })
            .withErrorListener(object: PermissionRequestErrorListener{
                override fun onError(error: DexterError?) {
                    Toast.makeText(this@MainActivity, "Error Occurred While Granting Permissions", Toast.LENGTH_SHORT).show()
                }

            })
            .onSameThread()
        .check()
    }
}


