package com.santanu.retrofitclient.model

import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("body")
    private val _text: String,

    @SerializedName("id")
    private val _id: Int,

    @SerializedName("title")
    private val _title: String,

    @SerializedName("userId")
    private val _userId: Int ) {


    val id: Int get() = _id
    val userId: Int get() = _userId
    val title: String get() = _title
    val text: String get() = _text
}