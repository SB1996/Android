package com.example.client

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.net.Socket
import java.net.UnknownHostException

@Suppress("BlockingMethodInNonBlockingContext")
class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var connToServer: Button
    private lateinit var send: Button
    private lateinit var massage: EditText
    private lateinit var clientStatus: TextView

    private var isClientConnected: Boolean = false

    /** class instance **/
    private lateinit var networkInfo: NetworkInfo

    /** socket instance **/
    private lateinit var socket: Socket

    /** input/output instance **/
    private lateinit var inputStream: DataInputStream
    private lateinit var outputStream: DataOutputStream

    private var HOST_ADDRESS: String? = null
    private val PORT: Int = 8080

    /** initialized activity view[widget] **/
    private fun initializedView(){
        connToServer = findViewById(R.id.btn_conn_server)
        send = findViewById(R.id.btn_send)
        massage = findViewById(R.id.ed_data)
        clientStatus = findViewById(R.id.tv_client_status)
    }

    /** initialized activity instance **/
    private fun initialized(){
        networkInfo = NetworkInfo(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializedView() /** initialized activity view[widget] call()**/

        initialized() /** initialized activity instance call() **/

        HOST_ADDRESS = networkInfo.getWifiNetworkIP()

        connToServer.setOnClickListener {
            if (!isClientConnected){
                isClientConnected = true
                connToServer.text = "Disconnect from Server"
                var isConnected: Boolean = false
                CoroutineScope(Dispatchers.IO).launch {
                    isConnected = connectedToServer(HOST_ADDRESS , PORT)
                }
            }else{
                isClientConnected = false
                connToServer.text = "Connect to Server"

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        socket.close()
                        visibilityView(false)
                    }catch (ex: IOException){
                        ex.printStackTrace()
                    }
                }
            }


        }

        send.setOnClickListener {
            val massage: String = massage.text.toString()
            Log.d(TAG, "MainActivity{} : onCreate() >> [line 93] :: Massage : $massage")
            CoroutineScope(Dispatchers.IO).launch {
                inputStream = DataInputStream(socket.getInputStream())
                outputStream = DataOutputStream(socket.getOutputStream())

                outputStream.writeUTF(massage)
                val recData: String = inputStream.readUTF()
                Log.d(TAG, "MainActivity{} : onCreate() >> [line 75] :: Data : $recData")
            }
        }
    }

    private suspend fun visibilityView(isVisible: Boolean){
        CoroutineScope(Dispatchers.Main).launch {
            if (isVisible){
                send.visibility = View.VISIBLE
                massage.visibility = View.VISIBLE
                clientStatus.visibility = View.VISIBLE
            }else{
                send.visibility = View.INVISIBLE
                massage.visibility = View.INVISIBLE
                clientStatus.visibility = View.INVISIBLE
            }
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun connectedToServer(hostAdd: String?, port: Int) : Boolean {

        var isConnected: Boolean = false

        var hostName: String? = null
        var hostAddress: String? = null

        try {
            socket = Socket(hostAdd, port)

            isConnected = true
            visibilityView(true)


        }catch (ex: IOException){
            ex.printStackTrace()
        }catch (ex: UnknownHostException){
            ex.printStackTrace()
        }

        hostName = socket.inetAddress.hostName
        hostAddress = socket.inetAddress.hostAddress

        Log.d(TAG, "MainActivity{} : connectedToServer() >> :: Host Name : $hostName")
        Log.d(TAG, "MainActivity{} : connectedToServer() >> :: Host Address : $hostAddress")


        return isConnected
    }
}
