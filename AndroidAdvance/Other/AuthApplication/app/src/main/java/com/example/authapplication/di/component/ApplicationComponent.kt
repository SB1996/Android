package com.example.authapplication.di.component

import com.example.authapplication.AuthApplication
import com.example.authapplication.di.module.ApplicationModule
import android.app.Application
import javax.inject.Singleton
import dagger.Component
import dagger.android.AndroidInjector
import dagger.BindsInstance
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class])
interface ApplicationComponent : AndroidInjector<AuthApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}