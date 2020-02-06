package com.example.mvvmarchitecture.data.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmarchitecture.listener.AuthLoginListener
import com.example.mvvmarchitecture.listener.AuthSingupListener

class AuthViewModel : ViewModel(), Observable{

    private val TAG: String = AuthViewModel::class.java.simpleName

    internal lateinit var authLoginListener: AuthLoginListener
    internal lateinit var authSingupListener: AuthSingupListener

    /** Login Data **/
    @Bindable var loginUsername: MutableLiveData<String> = MutableLiveData<String>()
    @Bindable var loginPassword: MutableLiveData<String> = MutableLiveData<String>()
    @Bindable var phoneNo: MutableLiveData<String> = MutableLiveData<String>()

    /** SingUp Data **/
    @Bindable var singupUserName: MutableLiveData<String> = MutableLiveData<String>()
    @Bindable var singupEmail: MutableLiveData<String> = MutableLiveData<String>()
    @Bindable var singupPassword: MutableLiveData<String> = MutableLiveData<String>()
    @Bindable var singupConfirmPassword: MutableLiveData<String> = MutableLiveData<String>()



    /** Login Process **/
    fun onLogin(){
        Log.d(TAG, "onLogin: called")
        authLoginListener.onLoginStart()
        val username: String = loginUsername.value.toString()
        val password: String = loginPassword.value.toString()

        if (username.isEmpty()  && password.isEmpty()){
            Log.d(TAG, "onLogin:: $username is Empty: ${username.isEmpty()} / $password is Empty: ${password.isEmpty()}")
            val errorMassage: String = "$username is Empty: ${username.isEmpty()} / $password is Empty: ${password.isEmpty()}"
            authLoginListener.onLoginError(errorMassage)
            authLoginListener.onLoginFailed()

        }else{
            Log.d(TAG, "onLogin:: Username: $username / Password: $password")
            authLoginListener.onLoginSuccess(username, password)
        }
    }

    /** Singup Process **/
    fun onSingup(){
        Log.d(TAG, "onSingup: called")
        authSingupListener.onSingupStart()
        val username: String = singupUserName.value.toString()
        val email: String = singupEmail.value.toString()
        val password: String = singupPassword.value.toString()
        val cPassword: String = singupConfirmPassword.value.toString()

        if (username.isEmpty()  && password.isEmpty()){
            Log.d(TAG, "onLogin:: $username is Empty: ${username.isEmpty()} / $password is Empty: ${password.isEmpty()}")
            val errorMassage: String = "$username is Empty: ${username.isEmpty()} / $password is Empty: ${password.isEmpty()}"
            authSingupListener.onSingupError(errorMassage)
            authSingupListener.onSingupFailed()

        }else{
            Log.d(TAG, "onLogin:: Username: $username / Password: $password")
            authSingupListener.onSingupSuccess(username, email, password)
        }

    }

    /** Firebase Google Singup **/
    fun googleSingup(){
        Log.d(TAG, "googleSingup: Called")
        authLoginListener.onFirebaseGoogleSingup()
    }

    /** Firebase Facebook Singup **/
    fun facebookSingup(){
        Log.d(TAG, "googleSingup: Called")
        authLoginListener.onFirebaseFacebookSingup()
    }




    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        Log.d(TAG, "removeOnPropertyChangedCallback: called")
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        Log.d(TAG, "addOnPropertyChangedCallback: called")
    }


}