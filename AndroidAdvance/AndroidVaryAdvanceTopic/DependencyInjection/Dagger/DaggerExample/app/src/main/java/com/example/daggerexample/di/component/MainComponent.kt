package com.example.daggerexample.di.component

import android.content.Context
import com.example.daggerexample.di.module.MainModule
import com.example.daggerexample.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [MainModule::class] )
interface MainComponent {

    fun injectMainActivity(activity: MainActivity)
}