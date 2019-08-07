package com.example.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class DownloadService : Service() {

    private val TAG = "Services"
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Services => onCreate() called")
        Log.i(TAG, "Services => onStartCommand() called")
    }
    private fun showNotification() {
        val builder = NotificationCompat.Builder(this, "channelId")
        builder.setSmallIcon(R.drawable.ic_announcement)
            .setContentText("This is service notificaion")
            .setContentTitle("Title")
        val notification = builder.build()
        startForeground(123, notification)

    }
    private fun downloadSong(song: String) {
        Log.i(TAG, "[${song}] Download Starting")
        try {
            Thread.sleep(20000)
        }catch (e: Exception){
            e.printStackTrace()
        }
        Log.i(TAG, "[${song}] Download Complited")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        showNotification()
        val songName: String = intent.getStringExtra(MainActivity.SONGS_KEY)!!
        val thread = Thread(Runnable {
            Log.d(TAG, "${songName} Downloading Preparing")
            downloadSong(songName)
            stopForeground(true)
            stopSelf()
        })
        thread.start()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "Services => onBind() called")

        return null
    }
    override fun onDestroy() {
        Log.i(TAG, "Services => onDestroy() called")
        super.onDestroy()
    }

}
