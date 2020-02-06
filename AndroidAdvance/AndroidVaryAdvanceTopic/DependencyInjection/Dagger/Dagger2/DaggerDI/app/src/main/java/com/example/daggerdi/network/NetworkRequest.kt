package com.example.daggerdi.network

import javax.inject.Inject

class NetworkRequest @Inject constructor(private val networkClient: NetworkClient) {

    fun sendRequest() : String{
        return networkClient.host
    }
}