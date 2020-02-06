package com.example.networkconnection.network

import android.annotation.SuppressLint
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*


class NetworkDetails private constructor() {

    companion object{
        internal fun build() : NetworkDetails {
            return NetworkDetails()
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