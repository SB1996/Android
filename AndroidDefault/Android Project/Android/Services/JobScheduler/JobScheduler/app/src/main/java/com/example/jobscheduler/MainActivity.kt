package com.example.jobscheduler

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.jobscheduler.Services.JobSchedulerService
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {

    companion object{
        private val TAG = "JobSchedulerServices"
        val SONGS_KEY = "Song_key"
        val MESSAGE_KEY: String = "Massage"
        val JOB_ID: Int = 1010
    }

    private lateinit var runBtn: Button
    private lateinit var cancleBtn: Button
    public lateinit var pgBar: ProgressBar

    private fun initliziing() {
        runBtn = findViewById(R.id.btn_run)
        cancleBtn = findViewById(R.id.btn_cancle)
        pgBar = findViewById(R.id.progressBar)
    }

    private val msgReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val result = intent.getStringExtra(MESSAGE_KEY)
            if(result == "DONE"){
                pgBar.setVisibility(View.INVISIBLE)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initliziing()

        runBtn.setOnClickListener {
            Log.i(TAG, "onCreate: Run Button Click")
            Toast.makeText(this@MainActivity, "Run Button Click", Toast.LENGTH_SHORT).show()
            pgBar.setVisibility(View.VISIBLE)

            val scheduler: JobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            val componentName: ComponentName = ComponentName(this, JobSchedulerService::class.java!!)
            val jobInfo = JobInfo.Builder(JOB_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setMinimumLatency(0)
                .build()

            val result = scheduler.schedule(jobInfo)
            if (result == JobScheduler.RESULT_SUCCESS)
                Log.d(TAG, "scheduleService: Job Scheduled")
            else
                Log.d(TAG, "scheduleService: Job not scheduled")
        }

        cancleBtn.setOnClickListener {
            Log.i(TAG, "onCreate: Cancle Button Click")
            Toast.makeText(this@MainActivity, "Cancle Button Click", Toast.LENGTH_SHORT).show()
            val scheduler: JobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            scheduler.cancel(JOB_ID)
            pgBar.setVisibility(View.INVISIBLE)
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(msgReceiver, IntentFilter(JobSchedulerService.SERVICE_MSG))
    }
    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(applicationContext)
            .unregisterReceiver(msgReceiver)
    }
}
