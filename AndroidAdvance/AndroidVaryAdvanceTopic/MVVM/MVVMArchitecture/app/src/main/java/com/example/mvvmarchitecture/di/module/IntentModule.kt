package com.example.mvvmarchitecture.di.module

import android.app.Activity
import android.content.Intent
import com.example.mvvmarchitecture.ui.activity.MainActivity
import com.example.mvvmarchitecture.ui.activity.auth.LoginActivity
import com.example.mvvmarchitecture.ui.activity.auth.SingupActivity
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class IntentModule(private val activity : Activity) {


    @Provides
    @Named("LoginActivityIntent")
    fun loginActivityIntentProvider() : Intent {
        return Intent(activity, LoginActivity::class.java)
    }

    @Provides
    @Named("SingupActivityIntent")
    fun singupActivityIntentProvider() : Intent {
        return Intent(activity, SingupActivity::class.java)
    }

    @Provides
    @Named("MainActivityIntent")
    fun mainActivityIntentProvider() : Intent {
        return Intent(activity, MainActivity::class.java)
    }
}