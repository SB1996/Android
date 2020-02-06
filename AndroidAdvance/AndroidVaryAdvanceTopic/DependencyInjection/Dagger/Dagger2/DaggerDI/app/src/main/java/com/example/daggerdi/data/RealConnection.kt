package com.example.daggerdi.data

import javax.inject.Inject

class RealConnection @Inject constructor() : Connection {

    override fun makeRequest(): Boolean {
        return true
    }

}