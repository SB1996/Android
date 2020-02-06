package com.example.authapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.authapplication.R

class SingupActivity : AppCompatActivity() {

    private lateinit var usernameET: EditText
    private lateinit var passwordET: EditText
    private lateinit var cPasswordET: EditText
    private lateinit var singUpBTN: Button
    private lateinit var toSingInTV: TextView

    private fun initialization(){
        usernameET = findViewById(R.id.SINGUPetUsername)
        passwordET = findViewById(R.id.SINGUPetPassword)
        cPasswordET = findViewById(R.id.SINGUPetCPassword)
        singUpBTN = findViewById(R.id.SINGUPbtnSingup)
        toSingInTV = findViewById(R.id.SINGUPtvGotoSingup)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)
        initialization()
    }
}
