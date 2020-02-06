package com.santanu.parcelable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.santanu.parcelable.data.ParcelableDetails
import kotlinx.android.synthetic.main.activity_main2.*


class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val data: ParcelableDetails? = intent.getParcelableExtra("data")

        if (data != null){
            tv_windows.append(
                "Name : ${data.name}\n" +
                "Username : ${data.username}\n"  +
                "Email : ${data.email}\n" +
                "Password : ${data.password}\n" +
                "Phone No : ${data.phone}\n"
            )
        }

    }
}
