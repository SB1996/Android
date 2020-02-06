package com.example.logingithub

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.tasks.OnSuccessListener
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.*


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

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Github Login"
        supportActionBar?.setLogo(R.drawable.ic_firebase_small)


        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser

        authBtn.setOnClickListener {
            val provider = OAuthProvider.newBuilder("github.com")
            val scopes = object : ArrayList<String>() {
                init {
                    add("user:email")
                }
            }
            provider.setScopes(scopes)

            firebaseAuth.startActivityForSignInWithProvider(this@MainActivity, provider.build())
            .addOnSuccessListener {
                Log.d(TAG, "onCreate: Login Succeed")
                Toast.makeText(this@MainActivity, "Login Succeed", Toast.LENGTH_SHORT).show()
                val pendingResultTask = firebaseAuth.pendingAuthResult

                if (pendingResultTask != null){
                    pendingResultTask
                        .addOnSuccessListener(
                            OnSuccessListener<AuthResult> { authResult ->
                                val authCredential: AuthCredential? = authResult?.credential
                                if (authCredential != null) {
                                    firebaseAuth.signInWithCredential(authCredential)
                                }
                            }
                        )
                        .addOnFailureListener {
                            Log.d(TAG, "onCreate: Exception : $it")

                        }

                }else{
                    Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()

                }

            }
            .addOnFailureListener {
                Log.d(TAG, "onCreate: Login Failed")
                Toast.makeText(this@MainActivity, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(firebaseUser != null){

            Log.d(TAG, "onStart: ${firebaseUser}")

            toolbar.visibility = View.VISIBLE
            authBtn.isEnabled = false
            statusMsg.text = getString(R.string.success)
            statusMsg.setTextColor(getColor(R.color.colorPrimary))
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
