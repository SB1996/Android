package com.example.gesturelistener

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView



class MainActivity : AppCompatActivity(), View.OnTouchListener,GestureDetector.OnGestureListener {

    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var gestureListenerView: TextView
    private var gestureListener: GestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gestureListener = GestureDetector(this,this)
        gestureListenerView = findViewById(R.id.tv_gesturelistener)

        gestureListenerView.setOnTouchListener(this)

    }

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        //Log.d(TAG, "onTouch: called")
        if(view?.id == R.id.tv_gesturelistener){
            val action: Int? = motionEvent?.action
            when(action){
                MotionEvent.ACTION_UP -> { Log.d(TAG, "onTouch: ACTION_UP") }
                MotionEvent.ACTION_DOWN -> { Log.d(TAG, "onTouch: ACTION_DOWN") }
                //MotionEvent.ACTION_MOVE -> { Log.d(TAG, "onTouch: ACTION_MOVE") }
                //MotionEvent.ACTION_CANCEL -> { Log.d(TAG, "onTouch: ACTION_CANCEL") }
                else -> { Log.d(TAG, "onTouch: Nothing") }
            }
        }

        //gestureListener?.onTouchEvent(motionEvent)
        return true
    }

    override fun onShowPress(p0: MotionEvent?) {
        Log.d(TAG, "onShowPress: called")
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        Log.d(TAG, "onSingleTapUp: called")

        return false
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        Log.d(TAG, "onDown: called")
        return false
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        Log.d(TAG, "onFling: called")
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        Log.d(TAG, "onScroll: called")
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
        Log.d(TAG, "onDown: called")
    }


}
