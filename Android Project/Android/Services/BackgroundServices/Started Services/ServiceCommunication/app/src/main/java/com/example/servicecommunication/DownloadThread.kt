package com.example.servicecommunication

import android.os.Looper

class DownloadThread : Thread() {
    var handler: DownloadHandler? = null
    override fun run() {
        Looper.prepare()
        handler = DownloadHandler()
        Looper.loop()
    }
}

