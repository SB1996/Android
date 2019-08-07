package com.example.facebooklogin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FacebookSinginActivity : AppCompatActivity() {

    private lateinit var facebookBtn: Button

    private val FacebookTAG: String = "FacebookTag"
    //Firebase instance ...!
    private lateinit var auth: FirebaseAuth
    //Facebook instance ...!
    private lateinit var callbackManager: CallbackManager

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
        setContentView(R.layout.activity_facebook_singin)
        Log.d(FacebookTAG, "onCreate() called")

        facebookBtn = findViewById(R.id.btn_facebook)
        //Facebook
        callbackManager = CallbackManager.Factory.create()
        //Firebase initializing...!
        auth = FirebaseAuth.getInstance()
        //showHashCode()
        Toast.makeText(this, "onCreate called", Toast.LENGTH_SHORT).show()
        facebookBtn.setOnClickListener {
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
                    Toast.makeText(this@FacebookSinginActivity, "Cancel", Toast.LENGTH_SHORT).show()

                }

                override fun onError(error: FacebookException) {
                    Log.d(FacebookTAG, "facebook:onError", error)
                    Toast.makeText(this@FacebookSinginActivity, "Error", Toast.LENGTH_SHORT).show()

                }
            })

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
            val info: PackageInfo = packageManager.getPackageInfo("com.example.facebooklogin", PackageManager.GET_SIGNATURES)
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
        if (user != null) {
            Toast.makeText(this@FacebookSinginActivity, "Login Successed : ${user}", Toast.LENGTH_SHORT).show()
            val profilePhoto = user.photoUrl
            val username = user.displayName
            val email = user.email
            val phone = user.phoneNumber
            Log.d("ff", profilePhoto.toString())
            val toMainActivity = Intent(this@FacebookSinginActivity, MainActivity::class.java)
                .putExtra("Photo", profilePhoto)
                .putExtra("Username", username)
                .putExtra("Email", email)
                .putExtra("Phone", phone)
            startActivity(toMainActivity)
        }else{
            Toast.makeText(this@FacebookSinginActivity, "Login Failed : ${user}", Toast.LENGTH_SHORT).show()
        }
    }
}
