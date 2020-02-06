package com.example.fragmenttofragment.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fragmenttofragment.R

class OneFragment : Fragment() {

    private var sendBtn: Button? = null
    private var massageEt: EditText? = null

    interface FragmentOneListener {
        fun onInputOneSent(massage: String)
    }
    private var listenerOne: FragmentOneListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentOneListener) {
            listenerOne = context
        } else {
            throw RuntimeException(context.toString() + " must implement listenerOne")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_one, container, false)
        massageEt = view.findViewById(R.id.et_massage_one)
        sendBtn = view.findViewById(R.id.btn_send)
        sendBtn!!.setOnClickListener {
            val massage = massageEt!!.getText().toString()
            Toast.makeText(context, "Massage : $massage", Toast.LENGTH_SHORT).show()
            listenerOne!!.onInputOneSent(massage)
            massageEt!!.getText().clear()
        }

        return view
    }
}
