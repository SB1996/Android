package com.example.mvvmarchitecture.di.module

import com.example.mvvmarchitecture.data.retrofit.UserRetrofit
import retrofit2.Retrofit
import retrofit2.create

class RetrofitModule {

    fun retrofitProvider() : Retrofit {
        return Retrofit.Builder().baseUrl("/").build()
    }

}