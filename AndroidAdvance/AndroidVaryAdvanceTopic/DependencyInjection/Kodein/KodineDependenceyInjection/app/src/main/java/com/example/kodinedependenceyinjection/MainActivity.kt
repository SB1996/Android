package com.example.kodinedependenceyinjection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kodinedependenceyinjection.bike.Bike
import org.kodein.di.Kodein
import org.kodein.di.android.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    private val TAG: String = MainActivity::class.java.simpleName

    override val kodein by kodein()

    val bike: Bike by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bike.drive()
    }



}

