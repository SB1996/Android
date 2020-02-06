package com.example.phonecall.broadcast

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import android.util.Log

class PhoneReceiver : BroadcastReceiver() {
    private val TAG: String = PhoneReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "PhoneReceiver{} : onReceive() >> [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Called")

        val packageManager: PackageManager = context.packageManager
        val hasPermission: Int = packageManager.checkPermission(
            Manifest.permission.READ_PHONE_STATE,
            context.packageName
        )

        if (hasPermission != PackageManager.PERMISSION_GRANTED){
            try {
                val state: String? = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
                val number: String? = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

                Log.d(TAG, "PhoneReceiver{} : onReceive() >> " +
                        "[line ${Thread.currentThread().stackTrace[2].lineNumber}] :: " +
                        "State : $state || Phone Number : $number"
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }


    }
}
