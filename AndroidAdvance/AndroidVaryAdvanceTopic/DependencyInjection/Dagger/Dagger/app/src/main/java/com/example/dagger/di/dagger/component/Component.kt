package com.example.dagger.di.dagger.component

import com.example.dagger.di.dagger.module.SharedPrefModule
import javax.inject.Singleton
import com.example.dagger.ui.activity.MainActivity
import dagger.Component

@Singleton
@Component( modules = [ SharedPrefModule::class ] )
interface SharedPrefComponent {
    fun sharedPrefInject(activity: MainActivity)
}
