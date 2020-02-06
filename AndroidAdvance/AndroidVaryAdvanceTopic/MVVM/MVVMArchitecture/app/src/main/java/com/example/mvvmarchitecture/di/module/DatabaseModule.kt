package com.example.mvvmarchitecture.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.mvvmarchitecture.data.db.MainDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

    @Provides
    @Singleton
    fun roomDatabaseProvider() : MainDatabase{
        return MainDatabase(context)
    }

}