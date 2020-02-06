package com.santanu.imageloading.retrofit_service

import com.santanu.imageloading.data.MovieData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApiService {
    companion object {
        fun create() : RetrofitApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.androidhive.info/")
                .build()
            return retrofit.create(RetrofitApiService::class.java)
        }
    }

    @GET("json/glide.json")
    fun getData() : Call<List<MovieData>>



}