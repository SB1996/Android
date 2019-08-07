package com.example.stack.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stack.R
import android.content.Intent
import com.example.stack.Applications


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Applications.isLogin == true){
            // User is login(Authenticated) ...!
        }else{
            // User is not login(Not Authenticated) ...!
            val intent = Intent(this, SinginActivity::class.java)
            startActivity(intent)
        }

    }
}
