package com.example.mvvmarchitecture.di.module

import android.app.Activity
import androidx.databinding.DataBindingUtil
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.databinding.ActivityLoginBinding
import com.example.mvvmarchitecture.databinding.ActivitySingupBinding
import com.example.mvvmarchitecture.ui.activity.auth.LoginActivity
import com.example.mvvmarchitecture.ui.activity.auth.SingupActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityBindingModule(private val activity : Activity) {

    @Provides
    fun activityLoginBindingProvider() : ActivityLoginBinding {
        return DataBindingUtil.setContentView<ActivityLoginBinding>(activity as LoginActivity, R.layout.activity_login)
    }

    @Provides
    fun activitySingupBindingProvider() : ActivitySingupBinding {
        return DataBindingUtil.setContentView<ActivitySingupBinding>(activity as SingupActivity, R.layout.activity_singup)
    }
}