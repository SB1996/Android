package com.example.fragment.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.fragment.Fragment.OneFragment
import com.example.fragment.Fragment.TwoFragment
import com.example.fragment.R

class MainActivity : AppCompatActivity() {
    private lateinit var rigth: Button
    private lateinit var left: Button
    private lateinit var remove: Button

    val fragmentManager = supportFragmentManager
    lateinit var fragmentTransaction: FragmentTransaction

    var isOneAdded: Boolean = false
    var isTwoAdded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rigth = findViewById(R.id.go_rigth)
        left = findViewById(R.id.go_left)
        remove = findViewById(R.id.go_rmv)


        rigth.setOnClickListener {
            fragmentTransaction = fragmentManager.beginTransaction()

            Log.d("TAG", "onCreate: ${fragmentTransaction.isEmpty}")

            if(!fragmentTransaction.isEmpty){
                val fragment: Fragment? = fragmentManager.findFragmentById(R.id.fragment_container)
                Toast.makeText(this@MainActivity, "Fragment Removed", Toast.LENGTH_SHORT).show()
                fragmentTransaction
                    .remove(fragment!!)
                    .replace(R.id.fragment_container, OneFragment())

                isOneAdded = true
                Toast.makeText(this@MainActivity, "FragmentOne replace", Toast.LENGTH_SHORT).show()
            }else{
                fragmentTransaction.replace(R.id.fragment_container, OneFragment())
                isOneAdded = true
                Toast.makeText(this@MainActivity, "FragmentOne added", Toast.LENGTH_SHORT).show()
            }
            fragmentTransaction.commit()

            Log.d("TAG", "onCreate: ${fragmentTransaction.isEmpty}")
        }

        left.setOnClickListener {
            fragmentTransaction = fragmentManager.beginTransaction()
            Log.d("TAG", "onCreate: ${fragmentTransaction.isEmpty}")

            if(!fragmentTransaction.isEmpty){
                val fragment: Fragment? = fragmentManager.findFragmentById(R.id.fragment_container)
                Toast.makeText(this@MainActivity, "Fragment Removed", Toast.LENGTH_SHORT).show()
                fragmentTransaction
                    .remove(fragment!!)
                    .replace(R.id.fragment_container, TwoFragment())

                isTwoAdded = true
                Toast.makeText(this@MainActivity, "FragmentTwo replace", Toast.LENGTH_SHORT).show()
            }else{
                fragmentTransaction.replace(R.id.fragment_container, TwoFragment())
                isTwoAdded = true
                Toast.makeText(this@MainActivity, "FragmentTwo added", Toast.LENGTH_SHORT).show()
            }
            fragmentTransaction.commit()

            Log.d("TAG", "onCreate: ${fragmentTransaction.isEmpty}")
        }

        remove.setOnClickListener {

            if(isOneAdded || isTwoAdded){
                if(isOneAdded){
                    fragmentTransaction = fragmentManager.beginTransaction()
                    val fragment: Fragment? = fragmentManager.findFragmentById(R.id.fragment_container)
                    fragmentTransaction
                        .remove(fragment!!)
                        .commit()
                    isOneAdded = false
                }
                if(isTwoAdded){
                    fragmentTransaction = fragmentManager.beginTransaction()
                    val fragment: Fragment? = fragmentManager.findFragmentById(R.id.fragment_container)
                    fragmentTransaction
                        .remove(fragment!!)
                        .commit()
                    isTwoAdded = false
                }
                Toast.makeText(this@MainActivity, "Fragment Removed", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@MainActivity, "Fragment not added yet", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
