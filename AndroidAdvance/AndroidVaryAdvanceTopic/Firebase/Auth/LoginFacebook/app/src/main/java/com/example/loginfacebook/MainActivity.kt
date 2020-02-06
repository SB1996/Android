package com.example.loginfacebook

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var firebaseAuth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null

    private var callbackManager: CallbackManager? = null

    private lateinit var toolbar: Toolbar
    private lateinit var statusProgress: ProgressBar
    private lateinit var authBtn: Button
    private lateinit var statusMsg: TextView

    private fun viewInitialization(){
        toolbar = findViewById(R.id.toolbar)
        statusProgress = findViewById(R.id.pbLoginStatus)
        authBtn = findViewById(R.id.btnAuth)
        statusMsg = findViewById(R.id.tvStatusMassage)
    }

    @SuppressLint("PackageManagerGetSignatures")
    private fun showHashCode(){
        Log.d(TAG, "showHashCode() called")
        try {
            val info: PackageInfo = packageManager.getPackageInfo("com.example.loginfacebook", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val messageDigest: MessageDigest = MessageDigest.getInstance("SHA")
                messageDigest.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.d("KeyHash", e.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.d("KeyHash", e.toString())
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewInitialization()
        //showHashCode()

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Facebook Login"
        supportActionBar?.setLogo(R.drawable.ic_firebase_small)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser

        callbackManager = CallbackManager.Factory.create()
        authBtn.setOnClickListener {
            Log.d(TAG, "Button Pressed")
            Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show()
            LoginManager.getInstance().logInWithReadPermissions(this, arrayListOf("email", "public_profile"))
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(TAG, "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook:onCancel")
                    Toast.makeText(this@MainActivity, "Cancel", Toast.LENGTH_SHORT).show()

                }

                override fun onError(error: FacebookException) {
                    Log.d(TAG, "facebook:onError", error)
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()

                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        if(firebaseUser != null){

            Log.d(TAG, "onStart: ${firebaseUser!!.displayName}")
            Log.d(TAG, "onStart: ${firebaseUser!!.email}")
            Log.d(TAG, "onStart: ${firebaseUser!!.providerData.get(0).email}")
            Log.d(TAG, "onStart: ${firebaseUser!!.providerData.get(1).email}")
            Log.d(TAG, "onStart: ${firebaseUser!!.isEmailVerified}")
            Log.d(TAG, "onStart: ${firebaseUser!!.photoUrl}")
            Log.d(TAG, "onStart: ${firebaseUser!!.phoneNumber.toString()}")
            Log.d(TAG, "onStart: ${firebaseUser!!.uid}")

            toolbar.visibility = View.VISIBLE
            authBtn.isEnabled = false
            statusMsg.text = getString(R.string.success)
            statusMsg.setTextColor(getColor(R.color.colorPrimary))
        }

    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential : Success")
                    Toast.makeText(baseContext, "Authentication Success", Toast.LENGTH_SHORT).show()
                    val user = firebaseAuth.currentUser

                    toolbar.visibility = View.VISIBLE
                    authBtn.isEnabled = false
                    statusMsg.text = getString(R.string.success)
                    statusMsg.setTextColor(getColor(R.color.colorPrimary))

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication Failed.", Toast.LENGTH_SHORT).show()

                }
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager?.onActivityResult(requestCode, resultCode, data)
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
                toolbar.visibility = View.INVISIBLE
                authBtn.isEnabled = true
                statusMsg.text = getString(R.string.failed)
                statusMsg.setTextColor(getColor(R.color.colorAccent))


                true
            }

            else -> { false }
        }
    }
}
