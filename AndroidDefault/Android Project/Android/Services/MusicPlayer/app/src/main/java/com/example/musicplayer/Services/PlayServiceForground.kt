package com.example.musicplayer.Services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.musicplayer.MainActivity
import com.example.musicplayer.R

class PlayServiceForground : Service() {

    private val TAG: String = "playService"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "playService class onStartCommand() called")
        showNotification()

        //stopForeground(true)
        //stopSelf()
        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent): IBinder? { return null}


    private fun showNotification() {
        val textTitle = "Santanu";
        val textContent = "Notification massage"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = ""
            val descriptionText = ""
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val CHANNEL_ID: String = "10"
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                this.name = name
                this.importance = importance
                this.description = descriptionText
            }
            //Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            //Create an explicit intent for an Activity in your app
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this@PlayServiceForground, 0, intent, 0)
            val builder = NotificationCompat.Builder(this,CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_pause)
                    .setContentTitle(textTitle)
                    .setContentText(textContent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            //to show notification ...!
            with(NotificationManagerCompat.from(this)) {
                //notificationId is a unique int for each notification that you must define
                val notificationId: Int = 100
                notify(notificationId, builder.build())
            }
            val notification = builder.build()

            startForeground(10,notification)
        }else{
            //Create an explicit intent for an Activity in your app
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this@PlayServiceForground, 0, intent, 0)
            val builder = NotificationCompat.Builder(this,"")
                    .setSmallIcon(R.drawable.ic_pause)
                    .setContentTitle(textTitle)
                    .setContentText(textContent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    //Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            //to show notification ...!
            with(NotificationManagerCompat.from(this)) {
                //notificationId is a unique int for each notification that you must define
                val notificationId: Int = 100
                notify(notificationId, builder.build())
            }
            val notification = builder.build()

            startForeground(10,notification)
        }
    }
}
