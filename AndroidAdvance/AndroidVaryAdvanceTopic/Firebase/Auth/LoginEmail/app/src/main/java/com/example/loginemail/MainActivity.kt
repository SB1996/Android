package com.example.loginemail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var toolbar: Toolbar
    private lateinit var statusProgress: ProgressBar
    private lateinit var authBtn: Button
    private lateinit var statusMsg: TextView
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText

    private lateinit var firebaseAuth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null

    private fun viewInitialization(){
        toolbar = findViewById(R.id.toolbar)
        statusProgress = findViewById(R.id.pbLoginStatus)
        authBtn = findViewById(R.id.btnAuth)
        statusMsg = findViewById(R.id.tvStatusMassage)
        emailEt = findViewById(R.id.etEmail)
        passwordEt = findViewById(R.id.etPassword)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewInitialization()

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Email Login"
        supportActionBar?.setLogo(R.drawable.ic_firebase_small)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser


        authBtn.setOnClickListener {
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                Log.d(TAG, "onCreate: Empty Field")
                Toast.makeText(this@MainActivity, "Empty Field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Log.d(TAG, "onCreate: Authentication Successful")
                    Toast.makeText(this@MainActivity, "Authentication Successful", Toast.LENGTH_SHORT).show()
                    
                }else{
                    Log.d(TAG, "onCreate: Authentication Failed")
                    Toast.makeText(this@MainActivity,"Authentication Failed",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnSuccessListener {
                Log.d(TAG, "onCreate: Authentication Request Success")
                Toast.makeText(this@MainActivity, "Authentication Request Success", Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                Log.d(TAG, "onCreate: Authentication Request Cancel")
                Toast.makeText(this@MainActivity,"Authentication Request Cancel",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.d(TAG, "Exception: $it")
                Log.d(TAG, "onCreate: Authentication Request Failed")
                Toast.makeText(this@MainActivity,"Authentication Request Failed",Toast.LENGTH_SHORT).show()
            }

        }

        firebaseAuth.addAuthStateListener {

            Log.d(TAG, "onCreate: addAuthStateListener call")

            firebaseUser = it.currentUser
            if(firebaseUser == null){

                Log.d(TAG, "onCreate : addAuthStateListener -> $firebaseUser")

                toolbar.visibility = View.INVISIBLE
                authBtn.isEnabled = true
                emailEt.isEnabled = true
                passwordEt.isEnabled = true
                statusMsg.text = getString(R.string.failed)
                statusMsg.setTextColor(getColor(R.color.colorAccent))
            }else{

                Log.d(TAG, "onCreate : addAuthStateListener -> $firebaseUser")

                toolbar.visibility = View.VISIBLE
                authBtn.isEnabled = false
                emailEt.isEnabled = false
                passwordEt.isEnabled = false
                statusMsg.text = getString(R.string.success)
                statusMsg.setTextColor(getColor(R.color.colorPrimary))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(firebaseUser != null){

            Log.d(TAG, "onStart: ${firebaseUser}")

            /*toolbar.visibility = View.VISIBLE
            authBtn.isEnabled = false
            emailEt.isEnabled = false
            passwordEt.isEnabled = false
            statusMsg.text = getString(R.string.success)
            statusMsg.setTextColor(getColor(R.color.colorPrimary))*/
        }

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

                toolbar.visibility = View.VISIBLE
                authBtn.isEnabled = false
                statusMsg.text = getString(R.string.success)
                statusMsg.setTextColor(getColor(R.color.colorPrimary))
                true
            }

            else -> { false }
        }
    }
}
