package com.example.fragmenttoactivity.Fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.fragmenttoactivity.R

class OneFragment : Fragment() {

    private var massegeET: EditText? = null
    private var sendBTN: Button? = null

    interface communicat {
        fun sendData(massage: String)
    }
    private var listener: communicat? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as communicat
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_one, container, false)
        massegeET = view.findViewById(R.id.et_massage)
        sendBTN = view.findViewById(R.id.btn_send)
        sendBTN!!.setOnClickListener {
            val text: String = massegeET!!.getText().toString()
            Toast.makeText(context, "Massage : ${text}", Toast.LENGTH_SHORT).show()
            listener!!.sendData(text)
            massegeET!!.getText().clear()
        }
        return view
    }

    /*override fun onDetach() {
        super.onDetach()
        listener = null
    }*/


}
