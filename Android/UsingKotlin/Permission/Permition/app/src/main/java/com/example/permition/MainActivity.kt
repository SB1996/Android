package com.example.permition

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.Toast
import android.Manifest;
import android.support.v4.app.ActivityCompat
import android.widget.Button
import android.content.DialogInterface
import android.support.v7.app.AlertDialog


class MainActivity : AppCompatActivity() {

    private lateinit var allPermissionBTN: Button
    private lateinit var cameraPermissionBTN: Button
    private lateinit var storagePermissionBTN: Button
    private lateinit var contactPermissionBTN: Button

    private val CAMERA_PERMISSION_CODE = 10
    private val STORAGE_PERMISSION_CODE = 11
    private val CONTACTS_PERMISSION_CODE = 12
    private val ALL_PERMISSION_CODE = 12

    private val permissionList: Array<String> = arrayOf<String>(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_CONTACTS
    )
    private val cameraPermission: String = Manifest.permission.CAMERA
    private val storagePermission: String = Manifest.permission.READ_EXTERNAL_STORAGE
    private val contactPermission: String = Manifest.permission.READ_CONTACTS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        allPermissionBTN = findViewById<Button>(R.id.btn_all_permission)
        cameraPermissionBTN = findViewById<Button>(R.id.btn_camera_permission)
        storagePermissionBTN = findViewById<Button>(R.id.btn_storage_permission)
        contactPermissionBTN = findViewById<Button>(R.id.btn_contact_permission)

        cameraPermissionBTN.setOnClickListener {
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT)
            requestPermission(cameraPermission, CAMERA_PERMISSION_CODE)
        }
        storagePermissionBTN.setOnClickListener {
            requestPermission(storagePermission, STORAGE_PERMISSION_CODE)
        }
        contactPermissionBTN.setOnClickListener {
            requestPermission(contactPermission, CONTACTS_PERMISSION_CODE)
        }


        allPermissionBTN.setOnClickListener {
            requestGroupPermission(permissionList, ALL_PERMISSION_CODE)
        }

    }

    private fun requestPermission(_Permission: String, _PermissionCode: Int) {
        if (ContextCompat.checkSelfPermission(this, _Permission) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You have already granted this permission!",Toast.LENGTH_SHORT).show();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, _Permission)) {
                
                AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because to accessed")
                    .setPositiveButton("ok", DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(this, arrayOf(_Permission), _PermissionCode)
                    })
                    .setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                    .create().show()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(_Permission), _PermissionCode)
            }
        }
    }


    private fun requestGroupPermission(_permissionsList: Array<String>, _permissionCode: Int){
        val permissionNeededList: Array<String> = _permissionsList
        var permissionDueList: Array<String> = arrayOf<String>()

        for(permission: String in permissionNeededList){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.size > 0) {
            ActivityCompat.requestPermissions(this, permissionDueList, _permissionCode)
        }else{
            Toast.makeText(this, "You have already granted all permission!", Toast.LENGTH_SHORT).show();
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "CAMERA : Permission GRANTED", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "EXTERNAL STORAGE : Permission GRANTED", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == CONTACTS_PERMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "CONTACT : Permission GRANTED", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
    }



}
