package com.example.normalthread

import android.nfc.Tag
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {

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

        runBtn.setOnClickListener {
            pgBar.setVisibility(View.VISIBLE)
            val thread: Thread = Thread(Runnable {
                try {
                    for (i in 0..10){
                        Log.i("BG_THREAD", "Background thread running .... : ${i}")
                        Thread.sleep(1000)
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            })
            thread.start()
        }


    }
}
