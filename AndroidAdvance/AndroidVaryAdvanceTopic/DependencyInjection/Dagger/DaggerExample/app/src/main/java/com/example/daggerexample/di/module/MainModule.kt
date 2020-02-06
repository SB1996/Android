package com.example.daggerexample.di.module

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class MainModule(val context: Context) {

    @Provides
    @Named("massage")
    fun getString() : String{
        return "i am injected, not by default"
    }

    @Provides
    @Named("anotherMassage")
    fun getAnotherString() : String{
        return context.packageName.toString()
    }
}