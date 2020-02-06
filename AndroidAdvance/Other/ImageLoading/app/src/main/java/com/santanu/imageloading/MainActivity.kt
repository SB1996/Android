package com.santanu.imageloading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.santanu.imageloading.adpter.RecyclerAdapter
import com.santanu.imageloading.data.MovieData
import com.santanu.imageloading.retrofit_service.RetrofitApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var recyclerView: RecyclerView

    private lateinit var retrofitService: RetrofitApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        retrofitService = RetrofitApiService.create()

        var movieData: List<MovieData>? = null

        retrofitService.getData().enqueue(object: Callback<List<MovieData>> {
            override fun onFailure(call: Call<List<MovieData>>, t: Throwable) {
                Log.d(TAG, "MainActivity{} : onFailure() >>" +
                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Error : ${t.message}"
                )
            }

            override fun onResponse(call: Call<List<MovieData>>, response: Response<List<MovieData>>) {
                if (!response.isSuccessful){return}

                movieData = response.body()


                if (movieData != null){
                    for (data: MovieData in movieData!!){
                        Log.d(TAG, "MainActivity{} : onResponse() >> [line 39] :: Name : ${data.name}")
                    }

                    recyclerView.adapter = RecyclerAdapter(applicationContext, movieData as ArrayList<MovieData>)


                }
            }

        })


    }
}
