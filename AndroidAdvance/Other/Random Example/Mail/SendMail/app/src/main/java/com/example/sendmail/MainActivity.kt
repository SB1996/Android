package com.example.sendmail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import javax.mail.internet.InternetAddress
import javax.mail.Message.RecipientType.TO
import javax.mail.internet.MimeMessage
import java.util.*
import android.widget.Toast
import android.os.AsyncTask
import android.util.Log
import javax.mail.*


class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var emailET: EditText
    private lateinit var emailOtpET: EditText
    private lateinit var sendMailBTN: Button

    internal fun initialization(){

        emailET = findViewById(R.id.etEmail)
        emailOtpET = findViewById(R.id.etEmailOTP)
        sendMailBTN = findViewById(R.id.btnAuth)

    }

    private var sendEmail: String? = null
    private var OTP: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialization()

        sendMailBTN.setOnClickListener {
            Log.d(TAG, "onCreate: Send Button Clicked")

            when(sendMailBTN.text){

                "Send Email" -> {
                    sendEmail = emailET.text.toString()
                    OTP = generateOtp()

                    if(sendEmail!!.isEmpty()){
                        Log.d(TAG, "onCreate: Please Enter your Mail Id")
                        Toast.makeText(this@MainActivity, "Mail is Empty Not Provided", Toast.LENGTH_SHORT).show()

                        return@setOnClickListener
                    }

                    if(sendMail(HostDetails.emailId, HostDetails.password)){
                        Log.d(TAG, "onCreate: Mail Send Succeed")
                        Toast.makeText(this@MainActivity, "Mail Send Succeed", Toast.LENGTH_SHORT).show()

                    }else{
                        Log.d(TAG, "onCreate: Mail Send Failed")
                        Toast.makeText(this@MainActivity, "Mail Send Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                "Verify" -> {

                }

                else -> {}
            }



        }
    }

    private fun sendMessage(recipients: String) {
        val senderThread = Thread(Runnable {
            try {
                val sender = MailSender(HostDetails.emailId, HostDetails.password)
                sender.sendMail(recipients)

            } catch (e: Exception) {
                Log.e("mylog", "Error: " + e.message)
            }
        })
        senderThread.start()
    }

    internal fun generateOtp() : String{ return Random().nextInt(999999).toString() }

    internal fun sendMail(username: String, password: String) : Boolean {

        var isSuccess: Boolean = false
        val mailHost = "smtp.gmail.com"
        val props = Properties()
        props.setProperty("mail.transport.protocol", "smtp")
        props.setProperty("mail.host", mailHost)
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.port"] = "465"
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.socketFactory.fallback"] = "false"
        props.setProperty("mail.smtp.quitwait", "false")

        val session = Session.getInstance(props, object:javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })

        try {

            val message: MimeMessage = MimeMessage(session)
            message.setFrom(InternetAddress(username))
            message.setRecipients(TO, InternetAddress.parse(sendEmail))
            message.subject = "Verify Your Email Account"
            message.setText("Your OTP : $OTP")

            SendMailTask().execute(message)

            isSuccess = true

        } catch (mex: MessagingException) {
            mex.printStackTrace()
        }

        return isSuccess
    }

    @SuppressLint("StaticFieldLeak")
    private inner class SendMailTask : AsyncTask<Message, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d(TAG, "onPreExecute: called")
        }

        override fun doInBackground(vararg messages: Message): String {
            Log.d(TAG, "doInBackground: called")
            /*for (msg in messages){
                Log.d(TAG, "doInBackground: ${msg.allRecipients}")
                Log.d(TAG, "doInBackground: ${msg.from}")
                Log.d(TAG, "doInBackground: ${msg.flags}")
                Log.d(TAG, "doInBackground: ${msg.folder}")
                Log.d(TAG, "doInBackground: ${msg.messageNumber}")
                Log.d(TAG, "doInBackground: ${msg.replyTo}")
                Log.d(TAG, "doInBackground: ${msg.subject}")
                Log.d(TAG, "doInBackground: ${msg.receivedDate}")
                Log.d(TAG, "doInBackground: ${msg.sentDate}")
            }*/
            try {
                for (msg in messages){
                    Transport.send(msg)
                }
                return "Success"
            } catch (ee: SendFailedException) {
                ee.printStackTrace()
                Log.d(TAG, "doInBackground: $ee")
                Log.d(TAG, "Exception : \n${ee.stackTrace}")
                return "error1"
            } catch (e: MessagingException) {
                e.printStackTrace()
                Log.d(TAG, "doInBackground: $e")
                Log.d(TAG, "Exception : \n${e.stackTrace.get(0)}")
                return "error2"
            }

        }

        override fun onPostExecute(result: String) {
            Log.d(TAG, "onPostExecute: called")
            if (result == "Success") {
                super.onPostExecute(result)

                Toast.makeText(this@MainActivity, "Mail Sent Successfully", Toast.LENGTH_LONG).show()

            }else if (result == "error1")
                Toast.makeText(this@MainActivity, "Email Failure", Toast.LENGTH_LONG).show()
            else if (result == "error2") {
                Toast.makeText(this@MainActivity, "Email Sent problem", Toast.LENGTH_LONG).show()
            }
        }
    }
}
