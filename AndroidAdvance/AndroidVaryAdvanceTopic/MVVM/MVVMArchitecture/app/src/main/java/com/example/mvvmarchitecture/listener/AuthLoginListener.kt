package com.example.mvvmarchitecture.listener

/** Listener for Authenticate Login **/
interface AuthLoginListener {

    fun onLoginStart()
    fun onLoginSuccess(username: String, password: String)
    fun onLoginFailed()
    fun onLoginError(errorMassage: String)


    fun onFirebaseGoogleSingup()
    fun onFirebaseFacebookSingup()
}