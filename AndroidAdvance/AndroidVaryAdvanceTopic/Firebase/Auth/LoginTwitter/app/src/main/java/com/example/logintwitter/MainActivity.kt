package com.example.logintwitter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {


    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var toolbar: Toolbar
    private lateinit var statusProgress: ProgressBar
    private lateinit var authBtn: Button
    private lateinit var statusMsg: TextView

    private lateinit var firebaseAuth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null

    private fun viewInitialization(){
        toolbar = findViewById(R.id.toolbar)
        statusProgress = findViewById(R.id.pbLoginStatus)
        authBtn = findViewById(R.id.btnAuth)
        statusMsg = findViewById(R.id.tvStatusMassage)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewInitialization()
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                true
            }

            else -> { false }
        }
    }
}
