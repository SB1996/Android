package com.example.asynctask

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class AsyncTaskActivity : AppCompatActivity() {
    private lateinit var runCodeBtn: Button
    private lateinit var cancleBtn: Button

    //innre class ...!
    class Asynctask: AsyncTask<String, String, String>() {
        val TAG: String = "AsyncTaskActivity"

        override fun onPreExecute() {
            //Log.d(TAG, "onPreExecute() called")
            /*for(i in 0..10){
                Log.d(TAG, "\tThread(onPreExecute) ${i} called")
                try {
                    Thread.sleep(1000)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }*/
        }
        override fun doInBackground(vararg data: String?): String {
            //Log.d(TAG, "doInBackground() called")
            /*for(i in 0..10){
                Log.d(TAG, "\t=>Thread(doInBackground) ${i} called")
                try {
                    Thread.sleep(1000)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }*/
            for(i: String? in data){
               Log.d(TAG,"${i} Download Start ...")
               try {
                   Thread.sleep(5000)
               }catch (e: Exception){
                   e.printStackTrace()
               }
               if (isCancelled()) break;
               Log.d(TAG,"${i} Download Completed")
            }
            publishProgress("... All Download Completed ...")

            return ""
        }
        override fun onProgressUpdate(vararg values: String?) {
            //Log.d(TAG, "onProgressUpdate() called")
            Log.d(TAG, values[0]!!)
            /*for(i in 0..10){
                Log.d(TAG, "\tThread(onProgressUpdate) ${i} called")
                try {
                    Thread.sleep(1000)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }*/
        }
        override fun onPostExecute(result: String?) {
            //Log.d(TAG, "onPostExecute() called")
            /*for(i in 0..10){
                Log.d(TAG, "\tThread(onPostExecute) ${i} called")
                try {
                    Thread.sleep(1000)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }*/
        }
        override fun onCancelled(result: String?) {
            //Log.d(TAG, "onCancelled(result: String?) called")
        }
        public override fun onCancelled() {
            //Log.d(TAG, "onCancelled() called")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task)
        runCodeBtn = findViewById(R.id.btn_run)
        cancleBtn = findViewById(R.id.btn_cancle)
        val songs: Array<String> = arrayOf("Song00", "Song01", "Song02", "Song03", "Song04")
        lateinit var task: Asynctask
        runCodeBtn.setOnClickListener {
            task = Asynctask()
            task.execute(*songs)
        }

        cancleBtn.setOnClickListener {

        }

    }
}
