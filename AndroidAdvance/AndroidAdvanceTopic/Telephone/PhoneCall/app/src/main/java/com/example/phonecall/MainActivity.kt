package com.example.phonecall

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.phonecall.permission.Permissions

class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    /** Phone Permission List **/
    private val phonePermissionList: Array<String> = arrayOf<String>(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.ANSWER_PHONE_CALLS,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_PHONE_NUMBERS
    )
    private val phonePermissionListAPI23: Array<String> = arrayOf<String>(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_PHONE_STATE
    )
    private val phonePermissionCode: Int = 1240 /** Phone Permission Code **/

    private lateinit var permissions: Permissions

    /** activity's instance initializer code **/
    private fun initializer(){
        permissions = Permissions(this )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializer() /** activity's instance initializer **/

        permissions.requestGroupPermission(phonePermissionListAPI23, phonePermissionCode)
    }
}
