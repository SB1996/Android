package com.example.livedata.mv

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityDataModel : ViewModel() {

    private val TAG: String by lazy { MainActivityDataModel::class.java.simpleName }

    @Bindable
    var data: MutableLiveData<String> = MutableLiveData<String>()

    private val _data = MutableLiveData<String>()
    val getData: LiveData<String>
        get() = _data

    fun onClickGoButton(){
        Log.d(TAG, "onClickGoButton: ${data.value}")
        _data.value = data.value
    }

}