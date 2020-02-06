package com.example.daggerdi.di

import com.example.daggerdi.MainActivity
import dagger.Component

@Component( modules = [MainActivityModule::class] )
interface MainActivityComponent {

    fun injectMainActivityField(mainActivity: MainActivity)

}