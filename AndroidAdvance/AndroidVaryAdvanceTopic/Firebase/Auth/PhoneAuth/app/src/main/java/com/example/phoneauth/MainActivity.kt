package com.example.phoneauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var toolbar: Toolbar
    private lateinit var statusProgress: ProgressBar
    private lateinit var authBtn: Button
    private lateinit var statusMsg: TextView
    private lateinit var phoneNo: EditText
    private lateinit var phoneOtp: EditText

    private lateinit var firebaseAuth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null

    private lateinit var storedVerificationCode: String
    private lateinit var storedVerificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private fun viewInitialization(){
        toolbar = findViewById(R.id.toolbar)
        statusProgress = findViewById(R.id.pbLoginStatus)
        authBtn = findViewById(R.id.btnAuth)
        statusMsg = findViewById(R.id.tvStatusMassage)
        phoneNo = findViewById(R.id.etPhoneNo)
        phoneOtp = findViewById(R.id.etPhoneNoOtp)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewInitialization()

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Phone Authentication"
        supportActionBar?.setLogo(R.drawable.ic_firebase_small)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser


        authBtn.setOnClickListener {
            val phoneNumber = phoneNo.text.toString()
            val phoneNumberWithCountryCode = "+91$phoneNumber"
            when(authBtn.text) {

                "Send OTP" -> {
                    if(phoneNumber.length != 10) {
                        Toast.makeText(this@MainActivity, "Please check your Number", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    //firebaseAuth.setLanguageCode("ES-es")
                    PhoneAuthProvider.getInstance().verifyPhoneNumber( phoneNumberWithCountryCode, 60, TimeUnit.SECONDS, this@MainActivity,
                        object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                                Log.d(TAG, "onVerificationCompleted: $phoneAuthCredential")
                                Log.d(TAG, "onVerificationCompleted: ${phoneAuthCredential.smsCode}")
                                Toast.makeText(this@MainActivity, "Verification Successful", Toast.LENGTH_SHORT).show()

                                phoneOtp.visibility = View.INVISIBLE
                                firebaseAuth.signInWithCredential(phoneAuthCredential)
                                    .addOnCompleteListener(this@MainActivity) {

                                        if(it.isSuccessful){
                                            Toast.makeText(this@MainActivity,"Authenticate Successful", Toast.LENGTH_SHORT ).show()
                                            toolbar.visibility = View.VISIBLE
                                            pbLoginStatus.visibility = View.INVISIBLE
                                            phoneNo.text.clear()
                                            phoneOtp.visibility = View.GONE
                                            authBtn.text = "Send OTP"
                                            phoneNo.isEnabled = false
                                            authBtn.isEnabled = false
                                            statusMsg.text = resources.getString(R.string.success)
                                            statusMsg.setTextColor(getColor(R.color.colorPrimary))
                                        }else{
                                            Toast.makeText(this@MainActivity,"Authenticate Failed", Toast.LENGTH_SHORT ).show()
                                            toolbar.visibility = View.INVISIBLE
                                            phoneNo.text.clear()
                                            phoneOtp.text.clear()
                                            phoneOtp.visibility = View.GONE
                                            authBtn.text = "Send OTP"
                                            phoneNo.isEnabled = true
                                            authBtn.isEnabled = true
                                        }

                                    }


                            }

                            override fun onVerificationFailed(e: FirebaseException) {
                                Log.d(TAG, "onVerificationFailed :: Exception $e")
                                Toast.makeText(this@MainActivity, "Some Exception Occur", Toast.LENGTH_SHORT).show()
                            }

                            override fun onCodeSent(verificationId: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken)
                                Log.d(TAG, "onCodeSent: $verificationId")
                                Log.d(TAG, "onCodeSent: $forceResendingToken")
                                pbLoginStatus.visibility = View.VISIBLE
                                phoneOtp.visibility = View.VISIBLE
                                authBtn.text = "Verify Your OTP"

                                storedVerificationId = verificationId
                                resendToken = forceResendingToken
                            }

                            override fun onCodeAutoRetrievalTimeOut(msg: String) {
                                super.onCodeAutoRetrievalTimeOut(msg)
                                Log.d(TAG, "onCodeAutoRetrievalTimeOut: $msg")
                                Toast.makeText(this@MainActivity, "Session Time Out", Toast.LENGTH_SHORT).show()
                            }

                        }
                    )
                }
                "Verify Your OTP" -> {
                    storedVerificationCode = phoneOtp.text.toString()
                    if(storedVerificationCode.isEmpty()){
                        return@setOnClickListener
                    }

                    val phoneCredential = PhoneAuthProvider.getCredential(storedVerificationId, storedVerificationCode)

                    Log.d(TAG, "onCreate: $phoneCredential")
                    Log.d(TAG, "onCreate: ${phoneCredential.smsCode}")
                    Log.d(TAG, "onCreate: ${phoneCredential.zza()}")
                    Log.d(TAG, "onCreate: ${phoneCredential.zzb()}")
                    Log.d(TAG, "onCreate: ${phoneCredential.zzc()}")
                    Log.d(TAG, "onCreate: ${phoneCredential.zzd()}")
                    Log.d(TAG, "onCreate: ${phoneCredential.zze()}")

                    firebaseAuth.signInWithCredential(phoneCredential)
                        .addOnCompleteListener(this@MainActivity) {

                            if(it.isSuccessful){
                                Toast.makeText(this@MainActivity,"Authenticate Successful", Toast.LENGTH_SHORT ).show()
                                toolbar.visibility = View.VISIBLE
                                pbLoginStatus.visibility = View.INVISIBLE
                                phoneNo.text.clear()
                                phoneOtp.visibility = View.GONE
                                authBtn.text = "Send OTP"
                                phoneNo.isEnabled = false
                                authBtn.isEnabled = false
                                statusMsg.text = resources.getString(R.string.success)
                                statusMsg.setTextColor(getColor(R.color.colorPrimary))
                            }else{
                                Toast.makeText(this@MainActivity,"Authenticate Failed", Toast.LENGTH_SHORT ).show()
                                toolbar.visibility = View.INVISIBLE
                                phoneNo.text.clear()
                                phoneOtp.text.clear()
                                phoneOtp.visibility = View.GONE
                                authBtn.text = "Send OTP"
                                phoneNo.isEnabled = true
                                authBtn.isEnabled = true
                            }

                        }
                }

                else -> {Toast.makeText(this@MainActivity, "Problem On Button Clicked", Toast.LENGTH_SHORT).show()}
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
                Toast.makeText(this@MainActivity, "Logout", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut()
                toolbar.visibility = View.INVISIBLE
                phoneNo.text.clear()
                phoneOtp.visibility = View.GONE
                phoneOtp.text.clear()
                authBtn.isEnabled = true
                phoneNo.isEnabled = true
                statusMsg.text = resources.getString(R.string.failed)
                statusMsg.setTextColor(getColor(R.color.colorAccent))

                true
            }

            else -> { false }
        }
    }
}
