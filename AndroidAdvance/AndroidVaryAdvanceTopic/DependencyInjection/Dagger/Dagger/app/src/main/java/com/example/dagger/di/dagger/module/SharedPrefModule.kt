package com.example.dagger.di.dagger.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class SharedPrefModule @Inject constructor(private val context: Context, private val title: String) {

    @Singleton
    @Provides
    fun provideContext() : Context {
        return context;
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context : Context) : SharedPreferences {
        return context.getSharedPreferences(title, Context.MODE_PRIVATE)
    }

}