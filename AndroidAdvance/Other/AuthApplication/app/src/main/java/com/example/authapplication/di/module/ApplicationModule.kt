package com.example.authapplication.di.module

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun getMassage() : String = "Dagger[Dependency Injection] Working"

    @Singleton
    @Provides
    @Named("SuccessMassage")
    fun getSuccessMassage() : String = "Dagger[Dependency Injection] Success"

    @Singleton
    @Provides
    @Named("FailedMassage")
    fun getFailedMassage() : String = "Dagger[Dependency Injection] Failed"


}