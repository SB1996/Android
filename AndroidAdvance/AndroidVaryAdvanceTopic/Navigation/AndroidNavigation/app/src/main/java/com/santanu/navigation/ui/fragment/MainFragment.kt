package com.santanu.navigation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.santanu.navigation.R

class MainFragment : Fragment() {
    private val TAG: String = MainFragment::class.java.simpleName

    private lateinit var mPaymentBtn: MaterialButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPaymentBtn = view.findViewById<MaterialButton>(R.id.m_btn_make_pay)

        mPaymentBtn.setOnClickListener { it: View? ->
            val navDirections: NavDirections = MainFragmentDirections.actionMainFragmentToCustomerFragment()
            findNavController().navigate(navDirections)
        }


    }


}
