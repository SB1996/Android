package com.example.servicecommunication.Activity

import android.content.BroadcastReceiver
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.servicecommunication.R
import com.example.servicecommunication.Services.DownloadService
import com.example.servicecommunication.SongsPlaylist
import android.content.Context
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.servicecommunication.DownloadHandler


class MainActivity : AppCompatActivity() {
    private val TAG = "Services"
    companion object{
        val SONGS_KEY = "Song_key"
        val MESSAGE_KEY: String = "Massage"
    }
    var startIntentToDownloadService: Intent? = null
    private lateinit var runBtn: Button
    private lateinit var cancleBtn: Button
    private lateinit var pgBar: ProgressBar
    private fun initliziing() {
        runBtn = findViewById(R.id.btn_run)
        cancleBtn = findViewById(R.id.btn_cancle)
        pgBar = findViewById(R.id.progressBar)
    }

    private val msgReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val songName = intent.getStringExtra(MESSAGE_KEY)
            Toast.makeText(this@MainActivity, "${songName} Downloaded", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(msgReceiver, IntentFilter(DownloadHandler.SERVICE_MSG))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initliziing()
        runBtn.setOnClickListener(View.OnClickListener {
            pgBar.setVisibility(View.VISIBLE)
            Toast.makeText(this@MainActivity, "Run Button Click", Toast.LENGTH_LONG).show()
            //send intent to download service
            for(song in SongsPlaylist.songsList){
                startIntentToDownloadService = Intent(this@MainActivity, DownloadService::class.java)
                startIntentToDownloadService?.putExtra(SONGS_KEY, song)
                startService(startIntentToDownloadService)
            }
        })
        cancleBtn.setOnClickListener {
            pgBar.setVisibility(View.INVISIBLE)
            Toast.makeText(this@MainActivity, "Cancle Button Click", Toast.LENGTH_LONG).show()
            //val stopIntentToDownloadService = Intent(this@MainActivity, DownloadService::class.java)
            if(startIntentToDownloadService != null) {
                stopService(startIntentToDownloadService)
            }
        }
    }
    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(applicationContext)
            .unregisterReceiver(msgReceiver)
    }
}
