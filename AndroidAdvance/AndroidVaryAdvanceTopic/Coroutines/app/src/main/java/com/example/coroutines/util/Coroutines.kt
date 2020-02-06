package com.example.coroutines.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutines {

    /** Run on Dispatchers.Main Coroutine **/
    fun onDispatchersMain(work: suspend (() -> Unit)) =
    CoroutineScope(Dispatchers.Main).launch {
        work()
    }


    /** Run on Dispatchers.IO Coroutine **/
    fun onDispatchersIO(work: suspend (() -> Unit)) =
    CoroutineScope(Dispatchers.IO).launch {
        work()
    }


    /** Run on Dispatchers.Default Coroutine **/
    fun onDispatchersDefault(work: suspend (() -> Unit)) =
    CoroutineScope(Dispatchers.Default).launch {
        work()
    }

}