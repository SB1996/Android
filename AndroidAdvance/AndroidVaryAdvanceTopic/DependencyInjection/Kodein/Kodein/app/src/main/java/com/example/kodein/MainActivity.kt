package com.example.kodein

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val TAG: String by lazy { MainActivity::class.java.simpleName }

    internal var sharedPreferencesName: String = "UserDetails"
    internal lateinit var sharePreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharePreference = getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
    }
}
