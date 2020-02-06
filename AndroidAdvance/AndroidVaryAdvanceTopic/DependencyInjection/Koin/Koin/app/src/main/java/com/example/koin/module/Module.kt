package com.example.koin.module

import com.example.koin.rc.Reference
import com.example.koin.rc.Reference00
import com.example.koin.ui.activity.MainActivity
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule: Module = module(override = true) {
    single<Reference> {
        Reference()
    }

    single<Reference00> {
        Reference00()
    }

    scope(named<MainActivity>()){

    }
}