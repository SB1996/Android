package com.example.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast

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
        Log.i(TAG, "MainActivity => onCreate() call")
        setContentView(R.layout.activity_main)
        initliziing()

        runBtn.setOnClickListener(View.OnClickListener {
            pgBar.setVisibility(View.VISIBLE)
            Log.i(TAG, "MainActivity : Run Button Click")
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
            Log.i(TAG, "MainActivity : Cancle Button Click")
            Toast.makeText(this@MainActivity, "Cancle Button Click", Toast.LENGTH_LONG).show()
            //val stopIntentToDownloadService = Intent(this@MainActivity, DownloadService::class.java)
            if(startIntentToDownloadService != null) {
                stopService(startIntentToDownloadService)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "MainActivity => onStart() call")
    }
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "MainActivity => onResume() call")
    }
    override fun onPause() {
        super.onPause()
        Log.i(TAG, "MainActivity => onPause() call")
    }
    override fun onStop() {
        super.onStop()
        Log.i(TAG, "MainActivity => onStop() call")
    }
    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "MainActivity => onRestart() call")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "MainActivity => onDestroy() call")
    }
}
