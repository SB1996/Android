package com.example.popupwindow

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPopupWindow.setOnClickListener {

            val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.popup_wondows_layout,null)

            val titleTV : TextView = layout.findViewById(R.id.tvTitle)
            val descriptionTV : TextView = layout.findViewById(R.id.tvDescription)
            val closeIV: ImageView = layout.findViewById(R.id.ivClose)
            val deleteIV: ImageView = layout.findViewById(R.id.ivDelete)
            val editIV: ImageView = layout.findViewById(R.id.ivEdit)

            titleTV.text = getString(R.string.title)
            descriptionTV.text = getString(R.string.description)

            val popupWindow = PopupWindow(
                layout,
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.LEFT
                popupWindow.enterTransition = slideIn

                val slideOut = Slide()
                slideOut.slideEdge = Gravity.RIGHT
                popupWindow.exitTransition = slideOut
            }

            TransitionManager.beginDelayedTransition(root_layout)
            popupWindow.showAtLocation(root_layout, Gravity.CENTER,0,0)

            closeIV.setOnClickListener {
                popupWindow.dismiss()
            }

            deleteIV.setOnClickListener {

            }

            editIV.setOnClickListener {

            }
        }
    }
}
