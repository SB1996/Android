package com.example.wifi.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class WiFiBroadcastReceiver : BroadcastReceiver() {

    private val TAG: String = WiFiBroadcastReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "WiFiBroadcastReceiver{} : onReceive() >> [line 10] :: Called")
    }
}
