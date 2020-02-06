package com.example.mvvmarchitecture.di.component

import com.example.mvvmarchitecture.di.module.*
import com.example.mvvmarchitecture.ui.activity.auth.SingupActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [
    IntentModule::class,
    ActivityBindingModule::class,
    ViewModelModule::class,
    FirebaseModule::class,
    DatabaseModule::class
] )
interface SingupActivityComponent {

    fun singupActivityInjector(activity: SingupActivity)
}