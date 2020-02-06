package com.santanu.retrofitclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.santanu.retrofitclient.model.PostData
import com.santanu.retrofitclient.retrofit_service.RetrofitApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var retrofit: RetrofitApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit = RetrofitApiService.create()

        /*retrofit.getPosts().enqueue(object : Callback<List<PostData>> {

            override fun onFailure(call: Call<List<PostData>>, t: Throwable) {
                Log.d(TAG, "MainActivity{} : onFailure() >>" +
                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Called"
                )

                Log.d(TAG, "MainActivity{} : onFailure() >>" +
                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Error : ${t.message}"
                )
            }

            override fun onResponse(call: Call<List<PostData>>, response: Response<List<PostData>>) {
                if ( ! response.isSuccessful) {
                    Log.d(TAG, "MainActivity{} : onResponse() >>" +
                        " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Response not Success"
                    )
                    return
                }

                val dataList: List<PostData>? = response.body()
                if (dataList != null) {
                    for (data: PostData in dataList){
                        Log.d(TAG, "MainActivity{} : onResponse() >> [line 48] :: Id : ${data.id}\n")
                        Log.d(TAG, "MainActivity{} : onResponse() >> [line 49] :: Id : ${data.userId}\n")
                        Log.d(TAG, "MainActivity{} : onResponse() >> [line 50] :: Id : ${data.title}\n")
                        Log.d(TAG, "MainActivity{} : onResponse() >> [line 51] :: Id : ${data.text}\n\n")

                    }
                }
            }


        })*/

        retrofit.getPost(5).enqueue(object : Callback<PostData> {
            override fun onFailure(call: Call<PostData>, t: Throwable) {
                Log.d(TAG, "MainActivity{} : onFailure() >>" +
                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Error : ${t.message}"
                )
            }

            override fun onResponse(call: Call<PostData>, response: Response<PostData>) {
                if (!response.isSuccessful){
                    Log.d(TAG, "MainActivity{} : onResponse() >>" +
                        " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Response not Success"
                    )
                    return
                }

                val data: PostData? = response.body()
                if (data != null){
                    Log.d(TAG, "MainActivity{} : onResponse() >> [line 76] :: Id : ${data.id}\n")
                    Log.d(TAG, "MainActivity{} : onResponse() >> [line 77] :: Id : ${data.userId}\n")
                    Log.d(TAG, "MainActivity{} : onResponse() >> [line 78] :: Id : ${data.title}\n")
                    Log.d(TAG, "MainActivity{} : onResponse() >> [line 79] :: Id : ${data.text}\n\n")
                }

            }

        })
    }
}


