package com.example.stack.Activity

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.stack.Applications
import com.example.stack.R
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class SingupwithActivity : AppCompatActivity() {
    private lateinit var useEmail: Button
    private lateinit var useFacebook: Button
    private lateinit var useGoogle: Button
    private lateinit var backLogin: TextView
    private val FacebookTAG: String = "FacebookTag"

    //Firebase instance ...!
    private lateinit var auth: FirebaseAuth
    //Facebook instance ...!
    private lateinit var callbackManager: CallbackManager

    //Connectivity ...!
    fun initConnection(){
        //Social Button initializing...!
        useEmail = findViewById(R.id.btn_email)
        useFacebook = findViewById(R.id.btn_facebook)
        useGoogle = findViewById(R.id.btn_google)

        backLogin = findViewById(R.id.tv_back_login)

        //Facebook
        callbackManager = CallbackManager.Factory.create()

        //Firebase initializing...!
        auth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        Log.d(FacebookTAG, "onStart() called")
        Toast.makeText(this, "onStart called", Toast.LENGTH_SHORT).show()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            updateUI(currentUser)
        }else{
            Toast.makeText(this, "User is not Login", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singupwith)
        initConnection()
        if(Applications.isLogin == true){
            //User is already login(Not need to Authenticated) ...!
            val intentToMain = Intent(this, MainActivity::class.java)
            startActivity(intentToMain)
        }else{

            //Login with Facebook Button ...!
            useFacebook.setOnClickListener {
                Log.d(FacebookTAG, "Button Pressed")
                Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show()
                LoginManager.getInstance().logInWithReadPermissions(this, arrayListOf("email", "public_profile"))
                LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d(FacebookTAG, "facebook:onSuccess:$loginResult")
                        handleFacebookAccessToken(loginResult.accessToken)
                    }

                    override fun onCancel() {
                        Log.d(FacebookTAG, "facebook:onCancel")
                        Toast.makeText(this@SingupwithActivity, "Cancel", Toast.LENGTH_SHORT).show()

                    }

                    override fun onError(error: FacebookException) {
                        Log.d(FacebookTAG, "facebook:onError", error as Throwable?)
                        Toast.makeText(this@SingupwithActivity, "Error", Toast.LENGTH_SHORT).show()

                    }
                })

            }

            //Login with Google Button ...!
            useGoogle.setOnClickListener {

            }

            useEmail.setOnClickListener {
                val intentToSingup = Intent(this, SingupActivity::class.java)
                startActivity(intentToSingup)
            }

            backLogin.setOnClickListener {
                val intentToSingin = Intent(this, SinginActivity::class.java)
                startActivity(intentToSingin)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(FacebookTAG, "onActivityResult() called")
        Toast.makeText(this, "onActivityResult called", Toast.LENGTH_SHORT).show()
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
    private fun showHashCode(){
        Log.d(FacebookTAG, "showHashCode() called")
        try {
            val info: PackageInfo = packageManager.getPackageInfo("com.example.loginfacebook", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val messageDigest: MessageDigest = MessageDigest.getInstance("SHA")
                messageDigest.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }
    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(FacebookTAG, "handleFacebookAccessToken() called")
        Log.d(FacebookTAG, "handleFacebookAccessToken : $token")
        Toast.makeText(this, "handleFacebookAccessToken called", Toast.LENGTH_SHORT).show()

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(FacebookTAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(FacebookTAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
    private fun updateUI(user: FirebaseUser?) {
        Log.d(FacebookTAG, "updateUI() called")
        Toast.makeText(baseContext, "Authentication Successed : ${user}", Toast.LENGTH_SHORT).show()
    }
}
