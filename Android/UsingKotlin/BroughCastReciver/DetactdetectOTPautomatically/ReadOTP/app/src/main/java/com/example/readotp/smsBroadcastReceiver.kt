package com.example.readotp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.widget.TextView
import android.widget.Toast


class smsBroadcastReceiver : BroadcastReceiver() {

    companion object{
        private lateinit var ONrSMS: ReceiveSMS.onReceiveSMS
        private const val RecevedSMS: String = "android.provider.Telephony.SMS_RECEIVED"
        private var TAG: String = "smsBroadcastReceiver"

        public lateinit var tv_txt: TextView
        fun setOTP(_tv_txt: TextView){
            smsBroadcastReceiver.tv_txt = _tv_txt
        }
    }
    private lateinit var massage: String
    private lateinit var phoneNO: String
    public lateinit var OTP: String



    override fun onReceive(context: Context, intent: Intent) {
        val massageData: Array<out SmsMessage>? = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        for (sms: SmsMessage  in massageData!!){
            massage = sms.messageBody
            OTP = massage.split(":")[1]
            phoneNO = sms.displayOriginatingAddress

        }
        tv_txt.setText(OTP)
        Toast.makeText(context, "Massage : $massage", Toast.LENGTH_LONG).show()



    }


}


