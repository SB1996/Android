package com.example.fragmenttoactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.fragmenttoactivity.Fragment.OneFragment


class MainActivity : AppCompatActivity(),OneFragment.communicat {


    private lateinit var updataMassageTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updataMassageTV = findViewById(R.id.tv_update_massage)

        supportFragmentManager.beginTransaction().add(R.id.fragment_contener, OneFragment()).commit()

    }

    override fun sendData(massage: String) {
        this.updataMassageTV.setText("Updated massage : ${massage}")
    }
}
