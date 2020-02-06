package com.santanu.barcodedata.di.module

import android.content.Context
import com.santanu.barcodedata.permission.Permissions
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class PermissionModule(private val context: Context) {

    @Provides
    fun provideContext() : Context {
        return context;
    }

    @Provides
    fun permissionProvider(context : Context) : Permissions {
        return Permissions(context)
    }
}