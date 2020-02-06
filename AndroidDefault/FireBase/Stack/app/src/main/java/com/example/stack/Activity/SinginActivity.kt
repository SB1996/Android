package com.example.stack.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.stack.Applications
import com.example.stack.R

class SinginActivity : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var fgPassword: TextView
    private lateinit var crNewAcc: TextView
    private lateinit var loginBtn: Button
    //View(XML) connectivity ...!
    fun veiwConnectivity(){
        username = findViewById(R.id.et_username)
        password = findViewById(R.id.et_password)
        loginBtn = findViewById(R.id.btn_facebook)
        fgPassword = findViewById(R.id.tv_fgpass)
        crNewAcc = findViewById(R.id.tv_back_login)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singin)
        veiwConnectivity()
        if(Applications.isLogin == true){
            //User is already login(Not need to Authenticated) ...!
            val intentToMain = Intent(this, MainActivity::class.java)
            startActivity(intentToMain)
        }else{
            //User is not login(need to Authenticated) ...!


            //Login Button click event
            loginBtn.setOnClickListener {

            }

            //Forgot password click event
            fgPassword.setOnClickListener {

            }

            //Change to SingupWithActivity from SinginActivity(New Singup Account)
            crNewAcc.setOnClickListener {
                val intentToSingup = Intent(this, SingupwithActivity::class.java)
                startActivity(intentToSingup)
            }


        }
    }

    override fun onBackPressed() {
        Toast.makeText(this,"Back Button Pressed", Toast.LENGTH_SHORT).show()
    }



}
