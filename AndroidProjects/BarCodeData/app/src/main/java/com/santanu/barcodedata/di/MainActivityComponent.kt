package com.santanu.barcodedata.di

import com.santanu.barcodedata.di.module.PermissionModule
import com.santanu.barcodedata.ui.activity.MainActivity
import dagger.Component

@Component(modules = [
    PermissionModule::class
])
interface MainActivityComponent {
    fun injectMainActivityField(mainActivity: MainActivity)
}