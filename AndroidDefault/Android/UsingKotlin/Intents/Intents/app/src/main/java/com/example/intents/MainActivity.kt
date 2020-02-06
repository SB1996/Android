package com.example.intents

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var edMassage: EditText
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edMassage = findViewById<EditText>(R.id.massage_et)
        nextButton  = findViewById<Button>(R.id.next_btn)

        nextButton.setOnClickListener {
            val massage = edMassage.text.toString()
            val url = "http://www.google.co.in"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}


