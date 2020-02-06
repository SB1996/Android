package com.example.ftpserver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ftpserver.permission.Permission
import com.example.ftpserver.utils.Directory
import net.rdrei.android.dirchooser.DirectoryChooserActivity
import org.apache.commons.net.ftp.FTPClient
import org.apache.ftpserver.FtpServer

class MainActivity : AppCompatActivity() {
    private val TAG: String by lazy { MainActivity::class.java.simpleName }

    private lateinit var permission: Permission
    private lateinit var directory: Directory

    private lateinit var ftpServer: FtpServer
    private lateinit var ftpClient: FTPClient

    private val requestDirectory: Int = 2108

    private fun initialized(){
        permission = Permission(this)
        directory = Directory(this)
    }

    private fun initializedView(){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*** initialized instance ***/
        initialized()

        permission.requestForAllPermissions()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == requestDirectory) {
            if (resultCode == DirectoryChooserActivity.RESULT_CODE_DIR_SELECTED) {
                if (data != null) {
                    // mDirAddress.text = data.getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR)
                }
            }
        }
    }
}
