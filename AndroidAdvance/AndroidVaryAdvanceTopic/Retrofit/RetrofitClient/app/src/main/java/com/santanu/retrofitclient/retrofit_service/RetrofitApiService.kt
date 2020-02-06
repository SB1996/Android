package com.santanu.retrofitclient.retrofit_service

import com.santanu.retrofitclient.model.PostData
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
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build()

            return retrofit.create(RetrofitApiService::class.java)
        }
    }


    @GET("/posts")
    fun getPosts() : Call<List<PostData>>

    @GET("/posts/{id}")
    fun getPost(@Path("id") id: Int) : Call<PostData>


}