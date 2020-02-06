package com.example.mvvmarchitecture.listener

/** Listener for Authenticate Singup **/
interface AuthSingupListener {

    fun onSingupStart()
    fun onSingupSuccess(username: String, email: String, password: String)
    fun onSingupFailed()
    fun onSingupError(errorMassage: String)
}