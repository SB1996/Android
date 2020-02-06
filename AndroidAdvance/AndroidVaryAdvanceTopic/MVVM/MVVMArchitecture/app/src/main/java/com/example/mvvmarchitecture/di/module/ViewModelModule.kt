package com.example.mvvmarchitecture.di.module

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmarchitecture.data.viewmodel.AuthViewModel
import com.example.mvvmarchitecture.ui.activity.auth.LoginActivity
import com.example.mvvmarchitecture.ui.activity.auth.SingupActivity
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ViewModelModule(private val activity : Activity) {

    @Provides
    @Named("LoginViewModel")
    fun loginViewModelProvider() : AuthViewModel {
        return ViewModelProviders.of(activity as LoginActivity).get(AuthViewModel::class.java)
    }

    @Provides
    @Named("SingupViewModel")
    fun singupViewModelProvider() : AuthViewModel {
        return ViewModelProviders.of(activity as SingupActivity).get(AuthViewModel::class.java)
    }

}