package com.example.service

import android.os.Handler
import android.os.Message
import android.util.Log
import com.example.service.Services.DownloadService
import java.lang.Exception

class DownloadHandler: Handler() {

    private val TAG = "Services"
    private lateinit var downloadService: DownloadService
    override fun handleMessage(msg: Message) {
        downloadSong(msg.obj.toString(),msg.arg1)
        downloadService.stopSelfResult(msg.arg1)
    }

    private fun downloadSong(song: String, id: Int) {
        Log.i(TAG, "[${song}] Download Starting")
        try {
            Thread.sleep(5000)
        }catch (e: Exception){
            e.printStackTrace()
        }
        Log.i(TAG, "[${song}] Download Complited")
    }
    fun setService(downloadService: DownloadService) {
        this.downloadService = downloadService
    }

}