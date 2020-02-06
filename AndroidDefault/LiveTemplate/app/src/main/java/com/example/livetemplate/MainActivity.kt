package com.example.livetemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: Live Template for Log.d()")
        Log.i(TAG, "onCreate: Live Template for Log.i()")
        Log.e(TAG, "onCreate: Live Template for Log.e()", null)
        Log.e(TAG, "onCreate: ")
        Toast.makeText(this@MainActivity, "", Toast.LENGTH_SHORT).show()
        Toast.makeText(this@MainActivity, "", Toast.LENGTH_SHORT).show()

    }
}
