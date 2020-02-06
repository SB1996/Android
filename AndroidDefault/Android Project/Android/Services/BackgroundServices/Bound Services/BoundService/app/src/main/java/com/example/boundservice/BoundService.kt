package com.example.boundservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class BoundService : Service() {
    private val TAG: String = "BoundService"

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Service class onCreate() called")
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "Service class onStartCommand() called")
        return super.onStartCommand(intent, flags, startId)
    }

    inner class MyServiceBinder : Binder() {
        val service: BoundService
            get() = this@BoundService
    }
    val mBinder: Binder = MyServiceBinder()
    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "Service class onBind() called")

        return mBinder
    }
    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "Service class onUnbind() called")
        return super.onUnbind(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Service class onDestroy() called")
    }
}
