package com.example.fragmenttofragment.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fragmenttofragment.Fragment.OneFragment
import com.example.fragmenttofragment.Fragment.TwoFragment
import com.example.fragmenttofragment.R

class MainActivity : AppCompatActivity(),OneFragment.FragmentOneListener {

    private lateinit var fragmenrOne: OneFragment
    private lateinit var fragmenrTwo: TwoFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmenrOne = OneFragment()
        fragmenrTwo = TwoFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_one, fragmenrOne)
            .replace(R.id.fragment_two, fragmenrTwo)
            .commit()
    }

    override fun onInputOneSent(massage: String) {
        fragmenrTwo.update(massage)
    }

}
