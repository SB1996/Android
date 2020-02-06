package com.santanu.retrofitclient.model

object Model {

    data class PostData(val body: String, val id: Int, val title: String, val userId: Int)
}