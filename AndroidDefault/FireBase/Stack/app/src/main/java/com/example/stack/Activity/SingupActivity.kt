package com.example.stack.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.stack.Applications
import com.example.stack.R

class SingupActivity : AppCompatActivity() {
    private lateinit var backLogin: TextView
    //View(XML) connectivity ...!
    fun veiwConnectivity(){
        backLogin = findViewById(R.id.tv_back_login)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)
        veiwConnectivity()
        if(Applications.isLogin == true){
            //User is already login(Not need to Authenticated) ...!
            val intentToMain = Intent(this, MainActivity::class.java)
            startActivity(intentToMain)
        }else{
            backLogin.setOnClickListener {
                val intentToSingin = Intent(this, SinginActivity::class.java)
                startActivity(intentToSingin)
            }


        }
    }
}
