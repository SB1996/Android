package com.example.fragmenttofragment.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fragmenttofragment.R

class TwoFragment : Fragment() {

    private var massageTv: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_two, container, false)
        massageTv = view.findViewById(R.id.tv_massage_two)

        return view
    }

    fun update(newText: String) {
        massageTv!!.setText(newText)
    }
}
