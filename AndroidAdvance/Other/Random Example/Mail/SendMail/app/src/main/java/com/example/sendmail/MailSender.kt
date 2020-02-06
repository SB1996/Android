package com.example.sendmail

import javax.activation.DataHandler
import javax.activation.DataSource
import javax.activation.FileDataSource
import javax.mail.BodyPart
import javax.mail.Message
import javax.mail.Multipart
import javax.mail.PasswordAuthentication
import javax.mail.Authenticator
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import java.security.Security.addProvider

import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.security.Security
import java.util.Properties
import javax.mail.util.ByteArrayDataSource
import android.app.ProgressDialog
import android.util.Log


class MailSender(private val mailId: String, private val password: String) : Authenticator() {

    private val mailHost = "smtp.gmail.com"
    private var session: Session? = null

    private var props: Properties

    private val _multipart = MimeMultipart()

    init {

        addProvider(JSSEProvider())

        props = Properties()
        props.setProperty("mail.transport.protocol", "smtp")
        props.setProperty("mail.host", mailHost)
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.port"] = "465"
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.socketFactory.fallback"] = "false"
        props.setProperty("mail.smtp.quitwait", "false")

        session = Session.getDefaultInstance(props, this)
    }

    override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(mailId, password)
    }

    @Synchronized
    @Throws(Exception::class)
    fun sendMail( recipients: String ) {
        try {
            val message = MimeMessage(session)
            val handler = DataHandler(
                ByteArrayDataSource("Success".toByteArray(), "text/plain")
            )
            message.sender = InternetAddress(mailId)
            message.subject = "Verify Your Email Account"
            message.dataHandler = handler
            val messageBodyPart = MimeBodyPart()
            messageBodyPart.setText("Success")
            _multipart.addBodyPart(messageBodyPart)

            // Put parts in message
            message.setContent(_multipart)
            if (recipients.indexOf(',') > 0)
                message.setRecipients( Message.RecipientType.TO, InternetAddress.parse(recipients) )
            else
                message.setRecipient(
                    Message.RecipientType.TO,
                    InternetAddress(recipients)
                )
            Transport.send(message)
        } catch (e: Exception) {

        }

    }

    @Throws(Exception::class)
    internal fun addAttachment(filename: String) {
        val messageBodyPart = MimeBodyPart()
        val source = FileDataSource(filename)
        messageBodyPart.dataHandler = DataHandler(source)
        messageBodyPart.fileName = "download image"

        _multipart.addBodyPart(messageBodyPart)
    }




    inner class ByteArrayDataSource(private var data: ByteArray?, private var type: String?) : DataSource {

        constructor(data: ByteArray) : this(data, null) {
            this.data = data
        }

        internal fun setType(type: String) {
            this.type = type
        }

        override fun getContentType(): String {
            return if (type == null) "application/octet-stream"
            else type!!
        }

        @Throws(IOException::class)
        override fun getInputStream(): InputStream {
            return ByteArrayInputStream(data)
        }

        override fun getName(): String {
            return "ByteArrayDataSource"
        }

        @Throws(IOException::class)
        override fun getOutputStream(): OutputStream {
            throw IOException("Not Supported")
        }
    }
}