package com.example.ftpclient.ui.activity

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ftpclient.HostDetails
import com.example.ftpclient.R
import com.example.ftpclient.permission.Permissions
import com.example.ftpclient.util.coroutines.Coroutines
import com.example.ftpclient.util.ftp.FTPClientUtils
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var connect: Button
    private lateinit var permissions: Permissions
    private lateinit var ftpClientUtils: FTPClientUtils

    /** Activity view initialized **/
    private fun initializedView(){
        connect= findViewById(R.id.btn_connect)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissions = Permissions(this)
        permissions.requestForStoragePermissions()
        permissions.requestForNetworkPermissions()

        initializedView()

        connect.setOnClickListener {
            ftpClientUtils = FTPClientUtils()

            val host: String =
                HostDetails.FTP_HOST_ADDRESS
            val port: Int =
                HostDetails.FTP_PORT_NO
            val username: String =
                HostDetails.FTP_USERNAME
            val password: String =
                HostDetails.FTP_PASSWORD

            var isServerConnected: Boolean
            var currentDirectory: String?
            var customDirectory: String?

            Coroutines.onDispatchersIO {
                isServerConnected = ftpClientUtils.connect(host, port, username, password)
                Log.d(
                    TAG, "MainActivity{} : onCreate() >>" +
                            " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Connected to Server : $isServerConnected"
                )
                if (isServerConnected) {
                    currentDirectory = ftpClientUtils.getCurrentWorkingDirectory()
                    Log.d(
                        TAG, "MainActivity{} : onCreate() >>" +
                                " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Directory : $currentDirectory"
                    )

                    if (ftpClientUtils.changeDirectory("Santanu")) {
                        Log.d(
                            TAG, "MainActivity{} : onCreate() >>" +
                                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Directory Changed Done"
                        )

                        currentDirectory = ftpClientUtils.getCurrentWorkingDirectory()
                        Log.d(TAG, "MainActivity{} : onCreate() >>" +
                            " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Directory : $currentDirectory"
                        )

                        val filesMap: HashMap<String, String>? = ftpClientUtils.getFilesMap(currentDirectory!!)
                        if (filesMap != null) {

                            val rootPath: String = Environment.getExternalStorageDirectory().toString()
                            val downloadPath: String = Environment.getExternalStorageDirectory().toString() + File.separator.toString() + Environment.DIRECTORY_DOWNLOADS

                            for (key in filesMap.keys){
                                Log.d(TAG, "MainActivity{} : onCreate() >>" +
                                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: $key : ${filesMap[key]}"
                                )
                                val file: String = key
                                val filePath: String? = filesMap[key]

                                if (ftpClientUtils.downloadFile("$currentDirectory/$file", "$downloadPath/$file")){
                                    Log.d(TAG, "MainActivity{} : onCreate() >>" +
                                            " [line ${Thread.currentThread().stackTrace[2].lineNumber}] ::" +
                                            " $file File Download succeeded at $downloadPath"
                                    )
                                }
                            }
                        }
                    } else {
                        Log.d(
                            TAG, "MainActivity{} : onCreate() >>" +
                                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Directory Changed Failed"
                        )
                    }
                }
            }
        }



    }
}
