package com.example.logingoogle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var toolbar: Toolbar
    private lateinit var statusProgress: ProgressBar
    private lateinit var authBtn: Button
    private lateinit var statusMsg: TextView

    private lateinit var firebaseAuth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null

    private val RC_SIGN_IN: Int = 10
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mGoogleSignInOptions: GoogleSignInOptions

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
        supportActionBar?.title = "Google Login"
        supportActionBar?.setLogo(R.drawable.ic_firebase_small)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser

        // Configure Google SignIn
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)

        authBtn.setOnClickListener {
            Toast.makeText(this@MainActivity, "Google Button Clicked", Toast.LENGTH_SHORT).show()
            googleSingIn()
        }

        firebaseAuth.addAuthStateListener {
            if(it.currentUser != null){
                toolbar.visibility = View.VISIBLE
                authBtn.isEnabled = false
                statusMsg.text = getString(R.string.success)
                statusMsg.setTextColor(getColor(R.color.colorPrimary))
            }else{
                toolbar.visibility = View.INVISIBLE
                authBtn.isEnabled = true
                statusMsg.text = getString(R.string.failed)
                statusMsg.setTextColor(getColor(R.color.colorAccent))
            }
        }

    }

    override fun onStart() {
        super.onStart()
        firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            Log.d(TAG, "onStart: already Authenticated")
            /*toolbar.visibility = View.VISIBLE
            authBtn.isEnabled = false
            statusMsg.text = getString(R.string.success)
            statusMsg.setTextColor(getColor(R.color.colorPrimary))*/

        }
    }

    private fun googleSingIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: DataIntent :: > $data")
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.d(TAG, "onActivityResult: $task")
            handleGoogleSingIn(task)
            /*try {
                val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                Log.d(TAG, "onActivityResult: $account")
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Toast.makeText(this@MainActivity, "Google SignIn Failed", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Google sign in failed", e)
            }*/
        }
    }

    private fun handleGoogleSingIn(task: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            Log.d(TAG, "handleGoogleSingIn: $account")
            firebaseAuthWithGoogle(account!!)
        } catch (e: ApiException) {
            Toast.makeText(this@MainActivity, "Google SignIn Failed", Toast.LENGTH_SHORT).show()
            Log.w(TAG, "Google sign in failed", e)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this@MainActivity, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
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
                true
            }

            else -> { false }
        }
    }
}
