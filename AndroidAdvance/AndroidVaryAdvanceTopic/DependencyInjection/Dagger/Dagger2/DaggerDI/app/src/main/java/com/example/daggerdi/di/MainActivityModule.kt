package com.example.daggerdi.di

import com.example.daggerdi.data.Connection
import com.example.daggerdi.data.RealConnection
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun bindsConnection(realConnection: RealConnection) : Connection
}