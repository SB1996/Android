package com.example.servicecommunication

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.servicecommunication.Activity.MainActivity
import com.example.servicecommunication.Services.DownloadService
import java.lang.Exception

class DownloadHandler: Handler() {

    private val TAG = "Services"
    companion object{
        val SERVICE_MSG: String = "Service Key"
    }
    private lateinit var downloadService: DownloadService
    private lateinit var hendlerContext: Context
    override fun handleMessage(msg: Message) {
        downloadSong(msg.obj.toString(),msg.arg1)
        downloadService.stopSelfResult(msg.arg1)
        sendMessageToUi(msg.obj.toString());
    }

    private fun sendMessageToUi(songName: String) {
        Intent(SERVICE_MSG).also {
            it.putExtra(MainActivity.MESSAGE_KEY,songName)
            LocalBroadcastManager.getInstance(hendlerContext).sendBroadcast(it)
        }

    }

    private fun downloadSong(song: String, id: Int) {
        Log.i(TAG, "[${song}] Download Starting")
        try {
            Thread.sleep(10000)
        }catch (e: Exception){
            e.printStackTrace()
        }
        Log.i(TAG, "[${song}] Download Complited")
    }
    fun setService(downloadService: DownloadService) {
        this.downloadService = downloadService
    }
    fun setContext(context: Context) {
        this.hendlerContext = context
    }

}