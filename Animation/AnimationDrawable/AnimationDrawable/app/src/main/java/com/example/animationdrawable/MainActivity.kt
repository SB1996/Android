package com.example.animationdrawable

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    private lateinit var rocketAnimation: AnimationDrawable

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialing()


    }
}
