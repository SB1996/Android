package com.example.jobscheduler.Services

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.jobscheduler.MainActivity
import com.example.jobscheduler.SongsPlaylist

class JobSchedulerService() : JobService() {

    private val TAG = "JobSchedulerServices"
    private var isJobCancelled = false
    private var isSuccess = false

    init{

    }
    companion object{
        val SERVICE_MSG: String = "Service Key"
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.i(TAG, "onStartJob: Called")
        Log.i(TAG, "Songs List Download Preparing")

        Thread(object : Runnable{
            override fun run() {
                for(song in SongsPlaylist.songsList) {
                    if(isJobCancelled)
                        return
                    Log.i(TAG, "[${song}] Downloading Start ...!")
                    try {
                        Thread.sleep(5000)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    Log.i(TAG, "[${song}] Downloading Completed")
                }
                isSuccess = true;
                jobFinished(params,isSuccess);
                if(isSuccess) {
                    sendMessageToUi()
                }
            }
        }).start()
        return true;
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        isJobCancelled = true
        return true;
    }

    private fun sendMessageToUi() {
        Log.i(TAG, "sendMessageToUi: called")
        Intent(SERVICE_MSG).also {
            it.putExtra(MainActivity.MESSAGE_KEY,"DONE")
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(it)
        }

    }


}
