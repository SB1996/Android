package com.example.googlelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSinginActivity : AppCompatActivity() {

    private lateinit var googleBtn: Button
    private val googleTag: String = "GoogleTag"

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    companion object{
        val RC_SIGN_IN: Int = 10
    }

    public override fun onStart() {
        super.onStart()
        Log.d(googleTag, "onStart() called")
        Toast.makeText(this, "onStart() called", Toast.LENGTH_SHORT).show()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            updateUI(currentUser)
        }else{
            Toast.makeText(this@GoogleSinginActivity, "Not Login", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_singin)
        Log.d(googleTag, "onCreate() called")
        Toast.makeText(this, "onCreate() called", Toast.LENGTH_SHORT).show()
        auth = FirebaseAuth.getInstance()
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.

        googleSignInClient = GoogleSignIn.getClient(this@GoogleSinginActivity, gso)

        googleBtn = findViewById(R.id.btn_google)
        googleBtn.setOnClickListener {
            Toast.makeText(this@GoogleSinginActivity, "Button click", Toast.LENGTH_SHORT).show()
            val signInIntent: Intent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(googleTag, "onActivityResult() called")
        Toast.makeText(this, "onActivityResult() called", Toast.LENGTH_SHORT).show()
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.d(googleTag, "Task : ${task.toString()}")

            try {
                // Google Sign In was successful, authenticate with Firebase
                //val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                val account = task.getResult(ApiException::class.java)
                Log.d(googleTag, "Account : ${account.toString()}")
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }
            }catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(googleTag, "Google sign in failed : ", e)
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(googleTag, "firebaseAuthWithGoogle() called")
        Log.d(googleTag, "firebaseAuthWithGoogle:" + acct.id)
        Toast.makeText(this, "firebaseAuthWithGoogle() called", Toast.LENGTH_SHORT).show()
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(googleTag, "signInWithCredential:success")
                val user = auth.currentUser
                updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(googleTag, "signInWithCredential:failure", task.exception)
                Toast.makeText(this@GoogleSinginActivity,"Authentication Failed.", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        Log.d(googleTag, "User : ${user}")

        if (user != null) {
            Toast.makeText(this@GoogleSinginActivity, "Login Successed : ${user}", Toast.LENGTH_SHORT).show()
            val profilePhoto = user.photoUrl
            val username = user.displayName
            val email = user.email
            val phone = user.phoneNumber

            val toMainActivity = Intent(this@GoogleSinginActivity, MainActivity::class.java)
                .putExtra("Phpto", profilePhoto)
                .putExtra("Username", username)
                .putExtra("Email", email)
                .putExtra("Phone", phone)
            startActivity(toMainActivity)
        }else{
            Toast.makeText(this@GoogleSinginActivity, "Login Failed : ${user}", Toast.LENGTH_SHORT).show()
        }
    }
}
