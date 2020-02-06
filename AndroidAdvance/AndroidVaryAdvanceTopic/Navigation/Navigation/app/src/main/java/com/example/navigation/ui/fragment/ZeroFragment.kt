package com.example.navigation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.navigation.R

class ZeroFragment : Fragment() {

    private val TAG: String = ZeroFragment::class.java.simpleName

    lateinit var navController: NavController

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        Log.d(TAG, "onCreateView: Called")
        return inflater.inflate(R.layout.fragment_zero, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: Called")

        navController = Navigation.findNavController(view)

        view.findViewById<Button>(R.id.btnSend).setOnClickListener {

            val senderName: String = view.findViewById<EditText>(R.id.etPersonName).text.toString()
            val bundle = bundleOf("SenderName" to senderName)

            navController.navigate(R.id.action_zeroFragment_to_oneFragment, bundle)

        }
    }



}
