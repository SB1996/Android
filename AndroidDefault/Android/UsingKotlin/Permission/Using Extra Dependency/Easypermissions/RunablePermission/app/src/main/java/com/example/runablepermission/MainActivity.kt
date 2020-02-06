package com.example.runablepermission

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {

    private lateinit var singlePermissionBtn: Button
    private lateinit var groupPermissionBtn: Button
    private val permissionCode: Int = 1
    private val permissionName: String = Manifest.permission.CAMERA

    private val permissionListCode: Int = 10
    private val permissionList: Array<String> = arrayOf<String>(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        singlePermissionBtn = findViewById(R.id.btn_single)
        groupPermissionBtn = findViewById(R.id.btn_group)

        singlePermissionBtn.setOnClickListener {
            if(EasyPermissions.hasPermissions(this@MainActivity, permissionName)){
                Toast.makeText(this@MainActivity,"CAMERA Permission already Granted", Toast.LENGTH_SHORT).show()
            }else{
                EasyPermissions.requestPermissions(this@MainActivity,"Permission Needed for Access",permissionCode,permissionName)
            }
        }
        groupPermissionBtn.setOnClickListener {

        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

        if(permissions.size == 1){
            if (requestCode == permissionCode) {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show()
                }
            }
        }
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
