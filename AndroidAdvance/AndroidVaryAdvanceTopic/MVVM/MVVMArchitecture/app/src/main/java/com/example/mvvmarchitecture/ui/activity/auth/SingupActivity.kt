package com.example.mvvmarchitecture.ui.activity.auth

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigator
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.data.db.MainDatabase
import com.example.mvvmarchitecture.data.db.dao.SingupUserDao
import com.example.mvvmarchitecture.data.db.entity.SingupUser
import com.example.mvvmarchitecture.data.repository.SingupUserData
import com.example.mvvmarchitecture.data.viewmodel.AuthViewModel
import com.example.mvvmarchitecture.databinding.ActivitySingupBinding
import com.example.mvvmarchitecture.di.component.DaggerSingupActivityComponent
import com.example.mvvmarchitecture.di.component.SingupActivityComponent
import com.example.mvvmarchitecture.di.module.*
import com.example.mvvmarchitecture.listener.AuthSingupListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_singup.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class SingupActivity : AppCompatActivity(), AuthSingupListener {

    private val TAG: String = SingupActivity::class.java.simpleName

    /** Component **/
    private lateinit var component: SingupActivityComponent

    /** Auth ViewModel Instance **/
    @field:[Inject Named("SingupViewModel")] lateinit var viewModel: AuthViewModel

    /** Activity Binding Instance **/
    @Inject lateinit var activitySingupBinding: ActivitySingupBinding

    @field:[Inject Named("LoginActivityIntent")] lateinit var toLoginActivityIntent: Intent

    /** Firebase Instance **/
    @Inject lateinit var firebaseAuth: FirebaseAuth
    @Inject lateinit var firebaseDatabase: FirebaseDatabase
    @Inject lateinit var firebaseFirestore: FirebaseFirestore

    /** Room Database Instance **/
    @Inject lateinit var database: MainDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)

        /** Setup DI Component **/
        component = DaggerSingupActivityComponent.builder()
            .activityBindingModule(ActivityBindingModule(this@SingupActivity))
            .intentModule(IntentModule(this@SingupActivity))
            .viewModelModule(ViewModelModule(this@SingupActivity))
            .databaseModule(DatabaseModule(this@SingupActivity))
        .build()
        component.singupActivityInjector(this@SingupActivity)


        /** ViewModel Binding to LoginActivity **/
        activitySingupBinding.apply {
            this.lifecycleOwner = this@SingupActivity
            this.singupViewModel = viewModel
        }

        /** AuthSingupListener Initializing **/
        viewModel.authSingupListener = this@SingupActivity


        /** Singup -> Login Activity **/
        singup_tvBackToLogin.setOnClickListener {
            Log.d(TAG, "onCreate: Go to Login Activity")

            toLoginActivityIntent.apply {
                startActivity(this@apply)
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "onBackPressed: Called")

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onSingupStart() {
        Log.d(TAG, "onSingupStart: Called")
    }
    override fun onSingupSuccess(username: String, email: String, password: String) {
        Log.d(TAG, "onSingupSuccess: Called")
        Log.d(TAG, "onSingupSuccess: Username: $username || Email: $email || Password: $password")

        val userData: SingupUserData = SingupUserData(username, email, password)

        /** Write Data on Firebase Database **/
        saveDataOnFirebaseDatabase(userData)
        /** Write Data on Firebase FireStore **/
        saveDataOnFirebaseFireStore(userData)
        /** Write Data on Database **/
        saveDataOnDatabase(userData)

    }
    override fun onSingupFailed() {
        Log.d(TAG, "onSingupFailed: Called")
    }
    override fun onSingupError(errorMassage: String) {
        Log.d(TAG, "onSingupError: Called")
        Log.d(TAG, "onSingupError: $errorMassage")
    }

    /** Save "userSingupData" on Firebase Realtime Database **/
    private fun saveDataOnFirebaseDatabase(data: SingupUserData){

        val firebaseDatabaseRef: DatabaseReference = firebaseDatabase.reference

        firebaseDatabaseRef.child("userSingupData")
            .child(data.username)
            .setValue(data)
            .addOnSuccessListener {
                Log.d(TAG, "addOnSuccessListener: called")
                Log.d(TAG, "addOnSuccessListener: data insert successful")
            }
            .addOnCompleteListener {
                Log.d(TAG, "addOnCompleteListener: called")
            }
            .addOnCanceledListener {
                Log.d(TAG, "addOnCanceledListener: called")
            }
            .addOnFailureListener {
                Log.d(TAG, "addOnFailureListener: called")
                Log.d(TAG, "Exception : ${it.message}")
            }
    }

    /** Save "userSingupData" on Firebase FireStore **/
    private fun saveDataOnFirebaseFireStore(data: SingupUserData){

        val dataHash = hashMapOf<String, String>(
            "username" to data.username,
            "email" to data.email,
            "password" to data.password
        )
        firebaseFirestore.collection("userSingupData")
            .document(data.username)
            .set(dataHash)
            .addOnCompleteListener {
                Log.d(TAG, "addOnCompleteListener: called")
            }
            .addOnSuccessListener {
                Log.d(TAG, "addOnSuccessListener: called")
                Log.d(TAG, "saveDataOnFirebaseFireStore: document insert successful")

            }
            .addOnFailureListener {
                Log.d(TAG, "addOnFailureListener: called")
                Log.d(TAG, "Exception : ${it.message} ")

            }
            .addOnCanceledListener {
                Log.d(TAG, "addOnCanceledListener: called")

            }
    }

    /** Save "userSingupData" on DataBase **/
    private fun saveDataOnDatabase(data: SingupUserData){
        Log.d(TAG, "saveDataOnDatabase: Called")

        val userData: SingupUser = SingupUser(
            data.username, data.email, data.password
        )
        val singupUserDao: SingupUserDao = database.getSingupUserDao()

        CoroutineScope(IO).launch {
            singupUserDao.insertSingupUser(userData)

            val user: SingupUser? = singupUserDao.getSingupUser(userData.username)

            if (user != null) {
                Log.d(TAG, "saveDataOnDatabase: data saved on database successful")
                Log.d(TAG, "saveDataOnDatabase: UserName: ${user.username} || Email: ${user.email} || Password: ${user.password}")
            }else{
                Log.d(TAG, "saveDataOnDatabase: Data Saved on Database Failed")
            }
        }

    }


}
