package com.example.broughcastreciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BrougthCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "BrougthCastReceiver Received", Toast.LENGTH_LONG).show()
    }
}
