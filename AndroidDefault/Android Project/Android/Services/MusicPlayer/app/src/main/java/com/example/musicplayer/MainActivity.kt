package com.example.musicplayer

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.musicplayer.Services.PlayService
import com.example.musicplayer.Services.PlayServiceForground

class MainActivity : AppCompatActivity() {

    private lateinit var prev: ImageButton
    private lateinit var prev_fd: ImageButton
    private lateinit var action: ImageButton
    private lateinit var next_fd: ImageButton
    private lateinit var next: ImageButton
    private fun initialing(){
        prev = findViewById(R.id.btn_prev)
        prev_fd = findViewById(R.id.btn_fd_prev)
        action = findViewById(R.id.btn_action)
        next_fd = findViewById(R.id.btn_fd_next)
        next = findViewById(R.id.btn_next)
    }
    private val TAG: String = "playService"
    private var isServiceBind: Boolean = false
    private lateinit var playBoundService: PlayService
    companion object{
        val MUSIC_KEY: String = "Music Key"
    }
    //Bind Service Connection
    private val mServiceConn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, iBinder: IBinder) {
            val myServiceBinder = iBinder as PlayService.MyServiceBinder
            playBoundService = myServiceBinder.service
            isServiceBind = true
            Log.d(TAG, "onServiceConnected")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "onServiceDisconnected")
        }
    }
    //Received BroadcastReceiver massage [to check song status]
    private val msgReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val result = intent.getStringExtra(MUSIC_KEY)
            if(result == "Song Completed"){
                action.setImageResource(R.drawable.ic_play)
            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialing()

        //Button to previous songs ...!
        prev.setOnClickListener {
            Toast.makeText(this@MainActivity, "Previous Button Clicked", Toast.LENGTH_SHORT).show()
        }

        //Button to forward_previous 10sec songs ...!
        prev_fd.setOnClickListener {
            Toast.makeText(this@MainActivity, "Forward previous Button Clicked", Toast.LENGTH_SHORT).show()
        }

        //Button to play/pause songs ...!
        action.setOnClickListener {
            Toast.makeText(this@MainActivity, "Play/Pause Button Clicked", Toast.LENGTH_SHORT).show()
            if(isServiceBind){
                if(playBoundService.isPlaying()){
                    action.setImageResource(R.drawable.ic_play)
                    playBoundService.pause()
                }else{
                    //start Background started services ...!
                    Intent(this@MainActivity, PlayService::class.java).apply {
                        startService(this);
                    }
                    action.setImageResource(R.drawable.ic_pause)
                    playBoundService.play()
                }
            }
        }

        //Button to forward_next 10sec songs ...!
        next_fd.setOnClickListener {
            Toast.makeText(this@MainActivity, "Forward Next Button Clicked", Toast.LENGTH_SHORT).show()
        }

        //Button to next songs ...!
        next.setOnClickListener {
            Toast.makeText(this@MainActivity, "Next Button Clicked", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onStart() {
        super.onStart()

        // Register Broadcast Receiver
        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(msgReceiver, IntentFilter(PlayService.MUSIC_COMPLITE))

        //Start Bound Services ...!
        Intent(this@MainActivity, PlayService::class.java).apply {
            bindService(this,mServiceConn, Context.BIND_AUTO_CREATE);
        }
    }
    override fun onStop() {
        super.onStop()

        // Unregister Broadcast Receiver
        LocalBroadcastManager.getInstance(applicationContext)
            .unregisterReceiver(msgReceiver)

        //Stop Bound Services ...!
        if (isServiceBind) {
            unbindService(mServiceConn)
            isServiceBind = false
        }
    }
    override fun onRestart() {
        super.onRestart()
        if(playBoundService.isPlaying()) {
            action.setImageResource(R.drawable.ic_pause)
        }else{
            action.setImageResource(R.drawable.ic_play)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        //start Forground started services ...!
        /*Intent(this@MainActivity, PlayServiceForground::class.java).apply {
            startService(this);
        }
        if(isServiceBind){
            if(playBoundService.isPlaying()){
                action.setImageResource(R.drawable.ic_play)
                //playBoundService.pause()
            }else{
                action.setImageResource(R.drawable.ic_pause)
                //playBoundService.play()
            }
        }*/

    }
}
