package com.example.livedata.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.livedata.R
import com.example.livedata.databinding.ActivityMainBinding
import com.example.livedata.mv.MainActivityDataModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName
    private var data: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(MainActivityDataModel::class.java)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            this.lifecycleOwner = this@MainActivity
            this.mainActivityViewModel = viewModel
        }

        val dataObserver = Observer<String> {
            tvShowText.text = it.toString()
        }
        viewModel.data.observe(this@MainActivity, dataObserver)

        Log.d(TAG, "onCreate: ${viewModel.getData.value}")

    }

}
