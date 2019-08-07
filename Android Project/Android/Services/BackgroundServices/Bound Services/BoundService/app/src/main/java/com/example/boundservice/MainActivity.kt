package com.example.boundservice

import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var runBtn: Button
    private lateinit var cancleBtn: Button
    private lateinit var pgBar: ProgressBar
    private fun initliziing() {
        runBtn = findViewById(R.id.btn_run)
        cancleBtn = findViewById(R.id.btn_cancle)
        pgBar = findViewById(R.id.progressBar)
    }
    private val TAG: String = "BoundService"
    private var isBind: Boolean = false
    private lateinit var boundService: BoundService

    private val mServiceConn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, iBinder: IBinder) {
            val myServiceBinder = iBinder as BoundService.MyServiceBinder
            boundService = myServiceBinder.service
            isBind = true
            Log.d(TAG, "onServiceConnected")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "onServiceDisconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initliziing()
        runBtn.setOnClickListener {
            Toast.makeText(this@MainActivity,"Run Button Clicked", Toast.LENGTH_SHORT).show()
        }
        cancleBtn.setOnClickListener {
            Toast.makeText(this@MainActivity,"Cancel Button Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this@MainActivity, BoundService::class.java).apply {
            bindService(this,mServiceConn, Context.BIND_AUTO_CREATE);
        }
    }
    override fun onStop() {
        super.onStop()
        if (isBind) {
            unbindService(mServiceConn)
            isBind = false
        }
    }
}
