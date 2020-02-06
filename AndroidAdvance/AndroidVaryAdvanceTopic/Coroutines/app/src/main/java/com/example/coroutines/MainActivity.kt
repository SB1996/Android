package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.coroutines.util.Coroutines
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_clear.setOnClickListener {
            textView.text = ""
        }

        btn_default.setOnClickListener {
            textView.text = ""
            /** Run on Dispatchers.Default Coroutine **/
            /*
            CoroutineScope(Dispatchers.Default).launch {
                fakeTask()
            }
            */
            Coroutines.onDispatchersDefault {
                fakeTask()
            }
        }

        btn_main.setOnClickListener {
            textView.text = ""
            /** Run on Dispatchers.Main Coroutine **/
            CoroutineScope(Dispatchers.Main).launch {
                fakeTask()
            }
        }

        btn_io.setOnClickListener {
            textView.text = ""
            /** Run on Dispatchers.IO Coroutine **/
            CoroutineScope(Dispatchers.IO).launch {
                fakeTask()
            }
        }
    }


    /** Fake Task **/
    private suspend fun fakeTask(){
        Log.d(TAG, "fakeTask: Start")
        Log.d(TAG, "fakeTask: run on Thread :: ${Thread.currentThread().name}")
        updateMassage("Task Start")
        for (i in 1..20){
            Log.d(TAG, "fakeTask: task progress in [Complete : ($i/20)].....")
            updateMassage("|---task progress in [Complete : ($i/20)]")
            Thread.sleep(500)
        }

        Log.d(TAG, "fakeTask: Completed")
        updateMassage("Task Complete")
    }

    private suspend fun updateMassage(massage : String) {
        withContext(Main){

            textView.append("$massage \n")
        }

    }
}
