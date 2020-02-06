package com.example.authapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.authapplication.R

class SinginActivity : AppCompatActivity() {

    private lateinit var usernameET: EditText
    private lateinit var passwordET: EditText
    private lateinit var loginBTN: Button
    private lateinit var toSingUpTV: TextView

    private fun initialization(){
        usernameET = findViewById(R.id.SINGINetUsername)
        passwordET = findViewById(R.id.SINGINetPassword)
        loginBTN = findViewById(R.id.SINGINbtnSingin)
        toSingUpTV = findViewById(R.id.SINGINtvGotoSingup)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singin)
        initialization()
    }
}
