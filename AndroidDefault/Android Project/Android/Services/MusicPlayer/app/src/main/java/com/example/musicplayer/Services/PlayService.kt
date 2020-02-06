package com.example.musicplayer.Services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.musicplayer.MainActivity
import com.example.musicplayer.R

class PlayService : Service() {

    private val TAG: String = "playService"
    inner class MyServiceBinder : Binder() {
        val service: PlayService
            get() = this@PlayService
    }
    val mBinder: Binder = MyServiceBinder()
    private lateinit var mPlayer: MediaPlayer
    companion object{
        val MUSIC_COMPLITE: String = "Music Complete"
    }
    fun isPlaying(): Boolean{ return mPlayer.isPlaying }
    fun play(): Unit{ mPlayer.start() }
    fun pause(): Unit{ mPlayer.pause() }
    fun stop(): Unit{ mPlayer.stop() }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "playService class onCreate() called")
        mPlayer = MediaPlayer.create(this@PlayService, R.raw.song)

        mPlayer.setOnCompletionListener {
            Intent(MUSIC_COMPLITE).also {
                it.putExtra(MainActivity.MUSIC_KEY,"Song Completed")
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(it)
                //Stop Started Services itSelf...!
                stopForeground(true)
                stopSelf()
            }
        }
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "Background playService class onStartCommand() called")
        showNotification()
        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "playService class onBind() called")

        return mBinder
    }
    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }
    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "playService class onUnbind() called")
        return true
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "playService class onDestroy() called")
        mPlayer.release()
    }
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
                this.setShowBadge(false)
            }
            //Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            //Create an explicit intent for an Activity in your app
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this@PlayService, 0, intent, 0)
            val builder = NotificationCompat.Builder(this,CHANNEL_ID)
                    // Show controls on lock screen even when user hides sensitive content.
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.ic_music_player)
                    .setContentTitle(textTitle)
                    .setContentText(textContent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Add media control buttons that invoke intents in your media service
                    .addAction(R.drawable.ic_skip_previous, "Previous", null)
                    .addAction(R.drawable.ic_pause, "Pause", null)
                    .addAction(R.drawable.ic_skip_next, "Next", null)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)

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
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this@PlayService, 0, intent, 0)
            val builder = NotificationCompat.Builder(this,"")
                    // Show controls on lock screen even when user hides sensitive content.
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.ic_music_player)
                    .setContentTitle(textTitle)
                    .setContentText(textContent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Add media control buttons that invoke intents in your media service
                    .addAction(R.drawable.ic_skip_previous, "Previous", null)
                    .addAction(R.drawable.ic_pause, "Pause", null)
                    .addAction(R.drawable.ic_skip_next, "Next", null)
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
