package com.example.service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.service.Services.DownloadService


class MainActivity : AppCompatActivity() {

    private val TAG = "Services"
    companion object{
        public val SONGS_KEY = "Song_key"
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

}


