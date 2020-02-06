package com.example.server.socket

import java.net.ServerSocket
import java.net.Socket

class Server {
    private val TAG: String = Server::class.java.simpleName

    private lateinit var serverSocket: ServerSocket
    private lateinit var socket: Socket

    internal fun startSocketServer(port: Int) {

        serverSocket = ServerSocket(port)
        socket = serverSocket.accept()
    }
}