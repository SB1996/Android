package com.example.daggerdependenceyinjection.di.component

import com.example.daggerdependenceyinjection.MainActivity
import com.example.daggerdependenceyinjection.di.module.MainModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [ MainModule::class ] )
interface MainComponent {

    fun mainInjection(activity : MainActivity)
}