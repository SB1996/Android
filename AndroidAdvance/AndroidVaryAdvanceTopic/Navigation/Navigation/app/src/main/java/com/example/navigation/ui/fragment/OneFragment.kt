package com.example.navigation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.navigation.R

class OneFragment : Fragment() {

    private val TAG: String = OneFragment::class.java.simpleName

    lateinit var navController: NavController

    private lateinit var senderName: String
    private lateinit var receiverName: String
    private lateinit var amount: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        senderName = arguments!!.getString("SenderName").toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: Called")

        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: Called")

        view.findViewById<TextView>(R.id.tvSenderPersonName).text = senderName

        navController = Navigation.findNavController(view)

        view.findViewById<Button>(R.id.btn_verified_send).setOnClickListener {

            receiverName = view.findViewById<EditText>(R.id.et_receivers_name).text.toString()
            amount = view.findViewById<EditText>(R.id.et_amount).text.toString()

            val bundle = bundleOf(
                "SenderName" to senderName,
                "ReceiverName" to receiverName,
                "Amount" to amount
            )

            navController.navigate(R.id.action_oneFragment_to_twoFragment, bundle)
        }
    }

}
