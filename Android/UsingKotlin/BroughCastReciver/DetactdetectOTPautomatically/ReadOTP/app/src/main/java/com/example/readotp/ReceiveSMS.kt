package com.example.readotp

interface ReceiveSMS{
    interface onReceiveSMS{
        fun onOTPReceived(otp: String): Unit
    }
}