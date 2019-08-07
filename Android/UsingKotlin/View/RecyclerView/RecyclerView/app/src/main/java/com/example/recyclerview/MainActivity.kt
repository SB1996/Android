package com.example.recyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class MainActivity : AppCompatActivity() {
    private var recycleView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycleView = findViewById<View>(R.id.recycle_view) as RecyclerView
        recycleView!!.layoutManager = LinearLayoutManager(this)
        val data = arrayOfNulls<String>(100)
        for (i in 0..99) {
            data[i] = "Content 0$i"
        }
        recycleView!!.adapter = RecycleAdapter(data)
    }
}
