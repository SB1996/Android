package com.example.intentservice.Services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.intentservice.Activity.MainActivity
import java.lang.Exception

class DownloadService : IntentService("DownloadService") {
    private val TAG = "Services"
    companion object{
        val SERVICE_MSG: String = "Service Key"
    }
    init {
        setIntentRedelivery(true)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate() called")

    }
    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent() called")
        val songName: String = intent?.getStringExtra(MainActivity.SONGS_KEY)!!
        Log.i(TAG, "==> [${songName}] prepare downloading")

        downloadSong(songName)
        sendMessageToUi(songName)
    }
    private fun downloadSong(song: String) {
        Log.i(TAG, "[${song}] Download Starting")
        try {
            Thread.sleep(10000)
        }catch (e: Exception){
            e.printStackTrace()
        }
        Log.i(TAG, "[${song}] Download Complited")
    }
    private fun sendMessageToUi(songName: String) {
        Intent(SERVICE_MSG).also {
            it.putExtra(MainActivity.MESSAGE_KEY,songName)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(it)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}
