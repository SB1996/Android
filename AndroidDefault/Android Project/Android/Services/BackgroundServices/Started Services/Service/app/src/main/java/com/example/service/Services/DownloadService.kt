package com.example.service.Services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Message
import android.util.Log
import com.example.service.MainActivity
import com.example.service.DownloadThread

class DownloadService : Service() {

    private val TAG = "Services"
    lateinit var downlaodThread: DownloadThread

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Services onCreate() called")
        downlaodThread = DownloadThread()
        downlaodThread.start()
        while (downlaodThread.handler == null){}
        downlaodThread.handler?.setService(this)
        Log.i(TAG, "Services onStartCommand() called")
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //Log.i(TAG, "Services onStartCommand() called")
        if(intent != null){
            val songName: String = intent.getStringExtra(MainActivity.SONGS_KEY)!!
            Log.i(TAG, "==> Services Queue created : [${songName}]")
            val message = Message.obtain()
            message.obj = songName
            message.arg1 = startId
            downlaodThread.handler?.sendMessage(message)
        }else{
            Log.i(TAG, "intent not received[Some problem occur]")
        }
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "Services onBind() called")

        return null
    }
    override fun onDestroy() {
        Log.i(TAG, "Services onDestroy() called")
        super.onDestroy()
    }
}
