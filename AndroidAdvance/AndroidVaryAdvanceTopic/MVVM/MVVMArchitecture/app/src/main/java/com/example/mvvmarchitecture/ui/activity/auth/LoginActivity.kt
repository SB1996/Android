package com.example.mvvmarchitecture.ui.activity.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.data.db.MainDatabase
import com.example.mvvmarchitecture.data.db.dao.SingupUserDao
import com.example.mvvmarchitecture.data.db.entity.SingupUser
import com.example.mvvmarchitecture.data.viewmodel.AuthViewModel
import com.example.mvvmarchitecture.databinding.ActivityLoginBinding
import com.example.mvvmarchitecture.di.component.DaggerLoginActivityComponent
import com.example.mvvmarchitecture.di.component.LoginActivityComponent
import com.example.mvvmarchitecture.di.module.ActivityBindingModule
import com.example.mvvmarchitecture.di.module.DatabaseModule
import com.example.mvvmarchitecture.di.module.IntentModule
import com.example.mvvmarchitecture.di.module.ViewModelModule
import com.example.mvvmarchitecture.listener.AuthLoginListener
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject
import javax.inject.Named

class LoginActivity : AppCompatActivity(), AuthLoginListener {

    private val TAG: String = LoginActivity::class.java.simpleName

    /** Component **/
    private lateinit var component: LoginActivityComponent

    /** Auth ViewModel Instance **/
    @field:[Inject Named("LoginViewModel")] lateinit var viewModel: AuthViewModel

    /** Activity Binding Instance **/
    @Inject lateinit var activityLoginBinding: ActivityLoginBinding

    @field:[Inject Named("SingupActivityIntent")] lateinit var toSingupActivityIntent: Intent
    @field:[Inject Named("MainActivityIntent")] lateinit var toMainActivityIntent: Intent

    /** Firebase Instance **/
    @Inject lateinit var firebaseAuth: FirebaseAuth
    @Inject lateinit var firebaseDatabase: FirebaseDatabase

    /** Room Database Instance **/
    @Inject lateinit var database: MainDatabase

    /** Google SingIn Instance **/
    private val googleSongInCode: Int = 10
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mGoogleSignInOptions: GoogleSignInOptions

    /** Facebook SingIn Instance **/
    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /** Setup DI Component **/
        component = DaggerLoginActivityComponent.builder()
            .activityBindingModule(ActivityBindingModule(this@LoginActivity))
            .intentModule(IntentModule(this@LoginActivity))
            .viewModelModule(ViewModelModule(this@LoginActivity))
            .databaseModule(DatabaseModule(this@LoginActivity))
        .build()
        component.loginActivityInjector(this@LoginActivity)

        /** ViewModel Binding to LoginActivity **/
        activityLoginBinding.apply {
            this.lifecycleOwner = this@LoginActivity
            this.loginViewModel = viewModel
        }
        /** AuthLoginListener Initializing **/
        viewModel.authLoginListener = this@LoginActivity


        /** Login -> Singup Activity **/
        login_tvGoToSingupPage.setOnClickListener {
            Log.d(TAG, "onCreate: Go to SingUp Activity")

            toSingupActivityIntent.apply {
                startActivity(this@apply)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = firebaseAuth.currentUser
        if (currentUser != null) {
            toMainActivityIntent.putExtra("currentAuthUser", currentUser)
            toMainActivityIntent.apply {
                startActivity(this@apply)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }


    override fun onLoginStart() {
        Log.d(TAG, "onLoginStart: Called")
    }
    override fun onLoginSuccess(username: String, password: String) {
        Log.d(TAG, "onLoginSuccess: Called")
        Log.d(TAG, "onLoginSuccess: Username : $username || Password : $password")

        val singupUserDao: SingupUserDao = database.getSingupUserDao()

        CoroutineScope(Dispatchers.IO).launch {
            val user: SingupUser? = singupUserDao.getSingupUser(username)

            if (user != null) {
                Log.d(TAG, "Database: UserName: ${user.username} || Email: ${user.email} || Password: ${user.password}")
                if(password == user.password){
                    Log.d(TAG, "onLoginSuccess: Password Match")

                    toMainActivityIntent.putExtra("currentAuthUser", user.username)
                    toMainActivityIntent.apply {
                        startActivity(this@apply)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                }
            }else{
                Log.d(TAG, "Database: Username not found")
            }
        }
    }
    override fun onLoginFailed() {
        Log.d(TAG, "onLoginFailed: Called")
    }
    override fun onLoginError(errorMassage: String) {
        Log.d(TAG, "onLoginError: Called")
        Log.d(TAG, "onLoginError: $errorMassage")
    }

    /** Google SingIn Process **/
    override fun onFirebaseGoogleSingup() {
        Log.d(TAG, "onFirebaseGoogleSingup: Called")

        /** Configure Google SignIn **/
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)

        val googleSignInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(googleSignInIntent, googleSongInCode)
    }
    private fun handleGoogleSingIn(task: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            Log.d(TAG, "handleGoogleSingIn: $account")
            firebaseAuthWithGoogle(account!!)
        } catch (e: ApiException) {
            Toast.makeText(this@LoginActivity, "Google SignIn Failed", Toast.LENGTH_SHORT).show()
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
                    val authUser = firebaseAuth.currentUser

                    toMainActivityIntent.putExtra("currentAuthUser", authUser)
                    toMainActivityIntent.apply {
                        startActivity(this@apply)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this@LoginActivity, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }


    /** Facebook SingIn Process **/
    override fun onFirebaseFacebookSingup(){
        Log.d(TAG, "onFirebaseFacebookSingup: Called")

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().logInWithReadPermissions(this, arrayListOf("email", "public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        })
    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                Log.d(TAG, "handleFacebookAccessToken: Called")

                val authUser = firebaseAuth.currentUser

                toMainActivityIntent.putExtra("currentAuthUser", authUser)
                toMainActivityIntent.apply {
                    startActivity(this@apply)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }

            }
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

    }/** Facebook SingIn Process End? **/



    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: DataIntent :: $data")

        /** Google Callback **/
        if (requestCode == googleSongInCode) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.d(TAG, "onActivityResult: $task")
            handleGoogleSingIn(task)
        }

        /** Facebook Callback **/
        if (callbackManager != null) {
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        Log.d(TAG, "onBackPressed: Called")
    }
}
