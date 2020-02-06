package com.example.server

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.server.data.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket

class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    /** view[widget] instance **/
    private lateinit var startServer: Button
    private lateinit var serverStatus: TextView

    private var isServerRunning: Boolean = false

    /** class instance **/
    private lateinit var networkInfo: NetworkInfo

    /** socket instance **/
    private lateinit var socket: Socket
    private lateinit var serverSocket: ServerSocket

    /** input/output instance **/
    private lateinit var inputStream: DataInputStream
    private lateinit var outputStream: DataOutputStream

    private var HOST_ADDRESS: String? = null
    private val PORT: Int = 8080

    /** initialized activity view[widget] **/
    private fun initializedView(){
        startServer = findViewById(R.id.btn_server_start)
        serverStatus = findViewById(R.id.tv_server_status)
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

        startServer.setOnClickListener {
            if (!isServerRunning) {

                Log.d(TAG, "MainActivity{} : onCreate() >>" +
                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Socket Server is Starting....."
                )

                startServer.text = "Stop Server"
                isServerRunning = true

                CoroutineScope(Dispatchers.IO).launch {
                    startSocketServer(PORT)
                }


            }else {
                Log.d(TAG, "MainActivity{} : onCreate() >>" +
                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Socket Server is Stop"
                )

                startServer.text = "Start Server"
                isServerRunning= false

                CoroutineScope(Dispatchers.IO).launch {
                    updateStatus(serverStatus, "Socket Server is Stop")
                    updateStatus(serverStatus, "Socket Server Offline")
                }

                Log.d(TAG, "MainActivity{} : onCreate() >>" +
                    " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Socket Server is Offline"
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun updateStatus(textView: TextView, massage: String){
        CoroutineScope(Dispatchers.Main).launch {
            textView.append("$massage \n")
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun startSocketServer(port: Int) {

        serverSocket = ServerSocket()
        serverSocket.reuseAddress = true
        serverSocket.bind(InetSocketAddress(port))

        updateStatus(serverStatus, "Socket Server is Starting..........")

        var canonicalHostName: String
        var clientName: String
        var clientAddress: String
        var receivedMassage: String

        while (isServerRunning){
            updateStatus(serverStatus, "|-----Waiting For Client Connection")
            try {
                socket = serverSocket.accept()

                inputStream = DataInputStream(socket.getInputStream()) /** request receiver from client **/
                outputStream = DataOutputStream(socket.getOutputStream()) /** response sender to client **/

            }catch (ex: IOException){
                ex.printStackTrace()
            }

            clientName = socket.inetAddress.hostName
            canonicalHostName = socket.inetAddress.canonicalHostName
            clientAddress = socket.inetAddress.hostAddress

            Log.d(TAG, "MainActivity{} : startSocketServer() >>" +
                "  [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: $clientAddress  is Connected"
            )

            updateStatus(serverStatus, "|-----Client : $clientAddress is Connected")

            /** received request from client **/
            receivedMassage = inputStream.readUTF()

            Log.d(TAG, "MainActivity{} : startSocketServer() >>" +
                " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Massage : $receivedMassage"
            )

            updateStatus(serverStatus, "|-----Client's Request : $receivedMassage")

            /** send response to client **/
            outputStream.writeUTF(DataRepository.data[receivedMassage].toString())

        }

        try {
            socket.close()
            inputStream.close()
            outputStream.flush()
            outputStream.close()

            serverSocket.close()
        }catch (ex: IOException){
            ex.printStackTrace()
        }
    }
}
