package com.example.contact.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.contact.R
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.Data.ContactUserData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.Adapter.ContactAdapter
import com.example.contact.Data.UserDataProvider
import com.example.contact.DataBase.ContactDatabaseManage
import android.util.Log


class MainActivity : AppCompatActivity() {

    private lateinit var mDataList: List<ContactUserData>
    private var mRecyclerView: RecyclerView? = null
    private lateinit var mContactUserData: ContactUserData
    private lateinit var mContactDatabaseManage: ContactDatabaseManage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.contact_recyclerView) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        mRecyclerView!!.adapter = ContactAdapter(UserDataProvider.userDataList, this)

        mContactDatabaseManage = ContactDatabaseManage(this)
        mContactDatabaseManage.openConnection()
        mContactDatabaseManage.seedDatabase()

        mRecyclerView!!.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //Toast.makeText(this@MainActivity, "Scrolling", Toast.LENGTH_SHORT).show()

                if(dy > 0){
                    Toast.makeText(this@MainActivity, "Scrolling Up", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@MainActivity, "Scrolling Down", Toast.LENGTH_SHORT).show()
                }

            }
        })

    }


    override fun onResume() {
        super.onResume()
        mContactDatabaseManage.openConnection()
    }
    override fun onPause() {
        super.onPause()
        mContactDatabaseManage.closeConnection()
    }
}

