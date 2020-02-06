package com.example.serviceasyntask.Service

import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.util.Log
import com.example.serviceasyntask.MainActivity
import java.lang.Exception

class DownloadService : Service() {

    class DownloadSong: AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            //Log.i(TAG, "onPreExecute() : Songs download Preparing")
        }
        override fun doInBackground(vararg songs: String?): String {
            for(song in songs){
                Log.i(TAG, "[${song}] Download Starting")
                try {
                    Thread.sleep(5000)
                }catch (e: Exception){
                    e.printStackTrace()
                }
                publishProgress(song)
            }
            return ".... All Songs download completed ...."
        }

        override fun onProgressUpdate(vararg songs: String?) {
            Log.i(TAG, "[${songs[0]}] Download Complited")
        }

        override fun onPostExecute(result: String) {
            //Log.i(TAG, result)
        }

    }
    companion object{
        public val TAG = "Services"
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Services onCreate() called")

        Log.i(TAG, "Services onStartCommand() called")
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //Log.i(TAG, "Services onStartCommand() called")
        if(intent != null){
            val songName: String = intent.getStringExtra(MainActivity.SONGS_KEY)!!
            Log.i(TAG, "==> Services Queue created : [${songName}]")
            val downloadSongs = DownloadSong()
            downloadSongs.execute(songName)

        }else{
            Log.i(TAG, "intent not received[Some problem occur]")
        }

        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "Services onBind() called")

        return null
    }
    override fun onDestroy() {
        Log.i(TAG, "Services onDestroy() called")
        super.onDestroy()
    }


}
