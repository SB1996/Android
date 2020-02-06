package com.example.butterknife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import butterknife.BindView
import butterknife.OnClick
import android.widget.TextView
import butterknife.ButterKnife


class MainActivity : AppCompatActivity() {

    @BindView(R.id.showtxt) lateinit var showtext: TextView

    @OnClick(R.id.click) fun btnClick(){
        showtext.setText("Data Binding Succeeded")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
    }
}
