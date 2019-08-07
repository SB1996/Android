package com.example.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar

class SplashActivity : AppCompatActivity() {
    private lateinit var progressbar: ProgressBar;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        progressbar = findViewById(R.id.progressBar)
        val intent = Intent(this, MainActivity::class.java)
        Thread(Runnable {
            try {
                for(i in 0..100){
                    Thread.sleep(50)
                    progressbar.setProgress(i)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }finally {
                progressbar.setProgress(0)
                startActivity(intent)
            }

        }).start()
    }
}
