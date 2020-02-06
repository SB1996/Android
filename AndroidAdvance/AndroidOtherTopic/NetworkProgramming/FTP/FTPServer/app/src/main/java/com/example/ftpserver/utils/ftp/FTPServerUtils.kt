package com.example.ftpserver.utils.ftp

import android.annotation.SuppressLint
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*
import kotlin.experimental.and

class FTPServerUtils {
    private val TAG: String = FTPServerUtils::class.java.simpleName


    /**
     * Convert byte array to hex string
     * @param bytes
     * @return
     */
    @SuppressLint("DefaultLocale")
    internal fun bytesToHex(bytes: ByteArray): String? {
        val stringBuffer: StringBuilder = java.lang.StringBuilder()
        for (idx in bytes.indices) {
            val intVal: Int = (bytes[idx] and 0xff.toByte()).toInt()
            if (intVal < 0x10) {
                stringBuffer.append("0")
            }
            stringBuffer.append(Integer.toHexString(intVal).toUpperCase())
        }
        return stringBuffer.toString()
    }

    /**
     * Get utf8 byte array.
     * @param str
     * @return  array of NULL if error was found
     */
    internal fun getUTF8Bytes(str: String): ByteArray? {
        return try {
            str.toByteArray(charset("UTF-8"))
        } catch (ex: java.lang.Exception) {
            null
        }
    }

    /**
     * Load UTF8withBOM or any ansi text file.
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    @Throws(IOException::class)
    internal fun loadFileAsString(filename: String): String? {
        val bufferLength: Int = 1024
        val inputFile: BufferedInputStream = BufferedInputStream(FileInputStream(filename), bufferLength)
        return try {
            val byteArrayOutputStream: ByteArrayOutputStream = ByteArrayOutputStream(bufferLength)
            val bytes: ByteArray = ByteArray(bufferLength)
            var isUTF8: Boolean = false
            var read: Int = 0
            var count: Int = 0
            while (inputFile.read(bytes).also({ read = it }) != -1) {
                if (count == 0 && bytes[0] == 0xEF.toByte() && bytes[1] == 0xBB.toByte() && bytes[2] == 0xBF.toByte() ) {
                    isUTF8 = true
                    byteArrayOutputStream.write(bytes, 3, read - 3) // drop UTF8 bom marker
                } else {
                    byteArrayOutputStream.write(bytes, 0, read)
                }
                count += read
            }
            if (isUTF8) {
                String(byteArrayOutputStream.toByteArray(), Charsets.UTF_8)
            } else {
                String(byteArrayOutputStream.toByteArray())
            }
        } finally {
            try {
                inputFile.close()
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
        }
    }

    /**
     * Get IP address from first non-localhost interface
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    @SuppressLint("DefaultLocale")
    internal fun getIPAddress(useIPv4: Boolean=false): String? {
        try {
            val interfaces: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (interf in interfaces) {
                val addresses: List<InetAddress> = Collections.list(interf.inetAddresses)
                for (address in addresses) {
                    if (!address.isLoopbackAddress) {
                        val serverAddress: String = address.hostAddress
                        // val isIPv4: Boolean = interAddressUtils.isIPv4Address(serverAddress);
                        val isIPv4: Boolean = serverAddress.indexOf(':') < 0
                        if (useIPv4) {
                            if (isIPv4) return serverAddress
                        } else {
                            if (!isIPv4) {
                                val deslim = serverAddress.indexOf('%') // drop ip6 zone suffix
                                return if (deslim < 0) {
                                    serverAddress.toUpperCase()
                                } else {
                                    serverAddress.substring(0, deslim).toUpperCase()
                                }
                            }
                        }
                    }
                }
            }
        } catch (ex: Exception) {
        } // for now eat exceptions
        return ""
    }

    /**
     * Returns MAC address of the given interface name.
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return  mac address or empty string
     */
    internal fun getMACAddress(interfaceName: String?): String? {
        try {
            val interfaces: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (interf in interfaces) {
                if (interfaceName != null) {
                    if (!interf.name.equals(interfaceName, ignoreCase = true)) continue
                }
                val mac: ByteArray = interf.hardwareAddress ?: return ""
                val buffer: StringBuilder = StringBuilder()
                for (index in mac.indices) buffer.append(String.format("%02X:", mac[index]))
                if (buffer.isNotEmpty()) {
                    buffer.deleteCharAt(buffer.length - 1)
                }
                return buffer.toString()
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return ""
        /**
        try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }
        **/
    }
}