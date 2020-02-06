package com.example.readotp.Activity

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.TextView
import android.widget.Toast
import com.example.readotp.R
import com.example.readotp.smsBroadcastReceiver


class MainActivity : AppCompatActivity() {

    private lateinit var setOPT: TextView

    companion object{
        private val PermissionCode: Int = 10
        private val permissionList: Array<String> = arrayOf<String>(
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_STATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestGroupPermission(permissionList, PermissionCode)
        setOPT = findViewById<TextView>(R.id.tv_show_opt)
        smsBroadcastReceiver.setOTP(setOPT)
    }


    // Runtime needed PERMISSION Request ...!
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
}
