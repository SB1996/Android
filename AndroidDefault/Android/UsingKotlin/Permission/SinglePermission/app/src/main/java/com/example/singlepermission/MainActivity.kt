package com.example.singlepermission

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.content.DialogInterface
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog


class MainActivity : AppCompatActivity() {

    private lateinit var singlePermissionBTN: Button;
    private lateinit var groupPrmissionBTN: Button;


    private val permissionName: String = Manifest.permission.CAMERA
    private val permissionCode: Int = 1

    private val permissionList: Array<String> = arrayOf<String>(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_CONTACTS
    )
    private val permissionListCode: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        singlePermissionBTN = findViewById(R.id.btn_single_permission)
        groupPrmissionBTN = findViewById(R.id.btn_gropu_permission)

        singlePermissionBTN.setOnClickListener {
            //request single permission
            requestSinglePermission(permissionName, permissionCode);
        }
        groupPrmissionBTN.setOnClickListener {

            //request group permission
            requestGroupPermission(permissionList, permissionListCode);
        }
    }


    //Single permission request ..!
    private fun requestSinglePermission(_permissionName: String, _permissionCode: Int) {
        if (ContextCompat.checkSelfPermission(this, _permissionName) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You have already granted this permission!", Toast.LENGTH_SHORT).show();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, _permissionName)) {
                AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for accessed Camera")
                    .setPositiveButton("Allow", DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(_permissionName), _permissionCode)
                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                    .create().show()

            } else {
                ActivityCompat.requestPermissions(this, arrayOf(_permissionName), _permissionCode)
            }
        }
    }

    //Group permission request ..!
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
        /*if(permissions.size == 1){
            if (requestCode == permissionCode) {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show()
                }
            }
        }*/
        if(permissions.size > 1){
            var status: Boolean = false
            if (requestCode == permissionCode) {
                if (grantResults.size > 0) {
                    for (_grantResults in grantResults){
                        if(_grantResults == PackageManager.PERMISSION_GRANTED){
                            status = true
                        }else{
                            status = false
                            break;
                        }
                    }
                    if(status == true) {
                        Toast.makeText(this, "All Permission GRANTED", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "All Permission Not GRANTED", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "All Permission Not GRANTED", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }
}
