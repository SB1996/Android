package com.example.navigation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.navigation.R

class TwoFragment : Fragment() {

    private val TAG: String = TwoFragment::class.java.simpleName

    lateinit var navController: NavController

    private lateinit var senderName: String
    private lateinit var receiverName: String
    private lateinit var amount: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        senderName = arguments!!.getString("SenderName").toString()
        receiverName = arguments!!.getString("ReceiverName").toString()
        amount = arguments!!.getString("Amount").toString()
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        Log.d(TAG, "onCreateView: Called")

        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: Called")

        navController = Navigation.findNavController(view)

        view.findViewById<TextView>(R.id.tv_sender_name).text = senderName
        view.findViewById<TextView>(R.id.tv_receiver_name).text = receiverName
        view.findViewById<TextView>(R.id.tv_amount_value).text = amount

        val bundle = bundleOf(
            "SenderName" to senderName,
            "ReceiverName" to receiverName,
            "Amount" to amount
        )

        view.findViewById<Button>(R.id.btn_verified).setOnClickListener {
            navController.navigate(R.id.action_twoFragment_to_threeFragment, bundle)
        }
    }


}
