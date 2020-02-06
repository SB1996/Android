package com.santanu.parcelable.data

import java.io.Serializable

data class SerializedDetails(
    val name: String?,
    val username: String?,
    val email: String?,
    val password: String?,
    val phone: String?) : Serializable {

}