package com.example.contact.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contact.R
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.Data.ContactUserData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.Adapter.ContactAdapter
import com.example.contact.Data.UserDataProvider

class MainActivity : AppCompatActivity() {

    private lateinit var mDataList: List<ContactUserData>
    private var mRecyclerView: RecyclerView? = null
    private lateinit var mContactUserData: ContactUserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.contact_recyclerView) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        mRecyclerView!!.adapter = ContactAdapter(UserDataProvider.userDataList, this)
    }
}
