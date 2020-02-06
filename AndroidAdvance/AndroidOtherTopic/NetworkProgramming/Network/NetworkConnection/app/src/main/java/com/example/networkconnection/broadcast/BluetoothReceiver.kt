package com.example.networkconnection.broadcast

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BluetoothReceiver : BroadcastReceiver() {
    private val TAG: String = BluetoothReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "BluetoothReceiver{} : onReceive() >>" +
            " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Called"
        )
        val bluetoothState: Int
        val bluetoothDevice: BluetoothDevice?
        when(intent.action){
            BluetoothAdapter.ACTION_STATE_CHANGED -> {
                bluetoothState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                when(bluetoothState){
                    BluetoothAdapter.STATE_OFF ->{
                        Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 23] :: Bluetooth Off")
                    }
                    BluetoothAdapter.STATE_ON -> {
                        Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 27] :: Bluetooth On")
                    }
                    BluetoothAdapter.STATE_TURNING_OFF -> {
                        Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 27] :: Bluetooth Turning Off")
                    }
                    BluetoothAdapter.STATE_TURNING_ON -> {
                        Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 27] :: Bluetooth Turning On")
                    }
                    BluetoothAdapter.STATE_CONNECTING -> {
                        Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 38] :: Connecting")
                    }
                    BluetoothAdapter.STATE_CONNECTED -> {
                        Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 35] :: Connected")
                    }
                    BluetoothAdapter.STATE_DISCONNECTING -> {
                        Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 41] :: Disconnecting")
                    }
                    BluetoothAdapter.STATE_DISCONNECTED -> {
                        Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 44] :: Disconnected")
                    }
                    BluetoothAdapter.SCAN_MODE_NONE -> {
                        Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 47] :: Bluetooth Scan None")
                    }
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE -> {
                        Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 50] :: Bluetooth Sean Found")
                    }
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE -> {
                        Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 53] :: Bluetooth Contactable")
                    }
                    BluetoothAdapter.ERROR -> {
                        Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 50] :: Bluetooth Error")
                    }
                }
            }
            BluetoothDevice.ACTION_ACL_CONNECTED -> {
                Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 61] :: Bluetooth Connected with Device")
                bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 64] :: Bluetooth Name : ${bluetoothDevice.name}")
                Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 65] :: Bluetooth Address : ${bluetoothDevice.address}")
                Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 66] :: Bluetooth Bond State : ${bluetoothDevice.bondState}")
            }
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 65] :: Bluetooth Disconnected with Device")
                bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 64] :: Bluetooth Name : ${bluetoothDevice.name}")
                Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 65] :: Bluetooth Address : ${bluetoothDevice.address}")
                Log.d(TAG, "BluetoothReceiver{} : onReceive() >> [line 66] :: Bluetooth Bond State : ${bluetoothDevice.bondState}")
            }
        }
    }
}
