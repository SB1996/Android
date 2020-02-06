package com.example.facebooklogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var drawer: androidx.drawerlayout.widget.DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var photo_iv: ImageView
    private lateinit var username_tv: TextView
    private lateinit var email_iv: TextView
    private lateinit var phone_iv: TextView
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar_main)
        photo_iv = findViewById(R.id.iv_Profile_pic)
        username_tv = findViewById(R.id.tv_username)
        email_iv = findViewById(R.id.tv_email)
        phone_iv = findViewById(R.id.tv_phone)

        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_nav_icon);
            toolbar.setTitle("Firebase Facebook Login");
            toolbar.setSubtitle("Santanu Banik");
            toolbar.visibility = View.VISIBLE
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite))
            toolbar.setSubtitleTextColor(getResources().getColor(R.color.colorWhite))
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    onBackPressed()
                    Toast.makeText(getApplicationContext(), "Navigation icon clicked", Toast.LENGTH_SHORT).show()
                    // rest code on clicking.....

                }
            })
        }
        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close)
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorWhite));

        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val data: Intent = intent
        if(data != null){
            val photo = data.getSerializableExtra("Photo")
            val username = data.getSerializableExtra("Username") as String
            val email = data.getSerializableExtra("Email") as String
            val phone = data.getSerializableExtra("Phone") as String


            username_tv.setText("Username : ${username}")
            phone_iv.setText("Phone No : ${phone}")
            email_iv.setText("Email : ${email}")
        }else{

        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.maim_manu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.memu_home -> {
                Toast.makeText(this@MainActivity, "Home", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.menu_setting -> {
                Toast.makeText(this@MainActivity, "Setting", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.menu_logout -> {
                Toast.makeText(this@MainActivity, "Logout", Toast.LENGTH_LONG).show()

                return true
            }

            else -> return super.onContextItemSelected(item)
        }
    }
}


