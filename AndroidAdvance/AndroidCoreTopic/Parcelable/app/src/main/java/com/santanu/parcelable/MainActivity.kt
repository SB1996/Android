package com.santanu.parcelable

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.santanu.parcelable.data.NonParcelableDetails
import com.santanu.parcelable.data.ParcelableDetails
import com.santanu.parcelable.data.SerializedDetails


class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var name: EditText
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var phone: EditText

    private lateinit var send: Button

    private fun init(){
        name = findViewById(R.id.ed_name)
        username = findViewById(R.id.ed_username)
        email = findViewById(R.id.ed_email)
        password = findViewById(R.id.ed_password)
        phone = findViewById(R.id.ed_phone)

        send = findViewById(R.id.btn_send)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        send.setOnClickListener {

            val _name: String = name.text.toString()
            val _username: String = username.text.toString()
            val _email: String = email.text.toString()
            val _password: String = password.text.toString()
            val _phone: String = phone.text.toString()

            val detailsData = ParcelableDetails(_name, _username, _email, _password, _phone)
            val detailsData00 = NonParcelableDetails(_name, _username, _email, _password, _phone)
            val detailsData01 = SerializedDetails(_name, _username, _email, _password, _phone)

            val intent = Intent(this@MainActivity, Main2Activity::class.java)
            intent.putExtra("data", detailsData)
            startActivity(intent)
        }
    }
}
