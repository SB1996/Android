package com.santanu.parcelable.data

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

data class ParcelableDetails(
    val name: String?,
    val username: String?,
    val email: String?,
    val password: String?,
    val phone: String?) : Parcelable{

    private val TAG: String = ParcelableDetails::class.java.simpleName

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        Log.d(TAG, "Details{} : constructor() >> [line 21] :: Secondary Constructor Called")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        Log.d(TAG, "Details{} : writeToParcel() >> [line 26] :: Called")
        if(dest != null){
            dest.writeString(name)
            dest.writeString(username)
            dest.writeString(email)
            dest.writeString(password)
            dest.writeString(phone)
        }

    }

    override fun describeContents(): Int {
        Log.d(TAG, "Details{} : describeContents() >> [line 38] :: Called")
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<ParcelableDetails> {
        override fun createFromParcel(parcel: Parcel): ParcelableDetails {
            return ParcelableDetails(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableDetails?> {
            return arrayOfNulls(size)
        }
    }

}