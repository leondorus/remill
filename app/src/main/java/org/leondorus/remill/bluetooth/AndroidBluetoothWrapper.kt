package org.leondorus.remill.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.BOND_BONDED
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.leondorus.remill.domain.sharing.FullDrugInfo
import java.io.IOException
import java.util.UUID

private const val TAG = "AndroidBluetoothWrapper"

@SuppressLint("MissingPermission")
class AndroidBluetoothWrapper(private val bluetoothAdapter: BluetoothAdapter) {
    val bluetoothEnabled: Boolean
        get() = bluetoothAdapter.isEnabled
    private val bluetoothDevicesFlow: MutableStateFlow<List<BluetoothDevice>> =
        MutableStateFlow(emptyList())

    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action: String = intent.action ?: return
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
                    if (device.bondState == BOND_BONDED) {
//                    if (true) {
                        bluetoothDevicesFlow.update { devices -> devices + device }
                    }
                }
            }
        }
    }

    private var activityBluetoothController: ActivityBluetoothController? = null
    private var sendThread: SendThread? = null
    private var receiveThread: ReceiveThread? = null

    fun getAvailableFriends(): StateFlow<List<BluetoothDevice>> {
        if (bluetoothEnabled) {
            bluetoothAdapter.startDiscovery()
        }
        return bluetoothDevicesFlow.asStateFlow()
    }

    fun sendToBluetoothDevice(
        bluetoothDevice: BluetoothDevice,
        fullDrugInfo: FullDrugInfo,
        afterSending: () -> Unit,
    ) {
        bluetoothAdapter.cancelDiscovery()

        sendThread = SendThread(bluetoothDevice, fullDrugInfo, afterSending)

        Log.d(TAG, "In send run")
        sendThread!!.start()
    }

    fun startReceiving(onReceive: (FullDrugInfo?) -> Unit) {
        receiveThread = ReceiveThread(onReceive)
        receiveThread!!.start()
    }

    fun makeDeviceDiscoverable() {
        activityBluetoothController?.makeDeviceDiscoverable()
    }

    fun tryEnablingBluetooth() {
        activityBluetoothController?.tryEnablingBluetooth()
    }

    fun attachActivityBluetoothController(activityBluetoothController: ActivityBluetoothController) {
        this.activityBluetoothController = activityBluetoothController
    }

    fun detachActivityBluetoothController(activityBluetoothController: ActivityBluetoothController) {
        this.activityBluetoothController = null
    }

    companion object {
        private const val NAME = "RemillApp"
        private val MY_UUID: UUID = UUID.fromString("16ee8608-70f2-4d2a-9b55-818ca893b086")
    }

    @OptIn(ExperimentalSerializationApi::class)
    private inner class ReceiveThread(private val onReceive: (FullDrugInfo?) -> Unit) : Thread() {

        private val serverSocket: BluetoothServerSocket by lazy(LazyThreadSafetyMode.NONE) {
            bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME, MY_UUID)
        }

        override fun run() {
            val socket: BluetoothSocket = try {
                Log.d(TAG, "Accept: trying to accept socket")
                val temp = serverSocket.accept()
                Log.d(TAG, "Accept: socket accepted")
                temp
            } catch (e: IOException) {
                Log.e(TAG, "Accept: socket's accept() method failed", e)
                return
            } finally {
                serverSocket.close()
            }

            val resultBytes = MutableList<Byte>(0) { 0 }
            val buffer = ByteArray(2048) { 0 }
            val inputStream = socket.inputStream
            while (true) {
                try {
                    val bytesNum = inputStream.read(buffer)
                    val subArray = buffer.copyOfRange(0, bytesNum)
                    if (bytesNum >= 0)
                        resultBytes.addAll(subArray.toList())
                    else
                        break
                } catch (e: IOException) {
                    Log.e(TAG, "Receiving: while reading got ${e.localizedMessage}")
                    break
                }
            }
            Log.d(TAG, "Receiving: final bytes: $resultBytes")

            val fullDrugInfo = ProtoBuf.decodeFromByteArray<FullDrugInfo>(resultBytes.toByteArray())
            val fullDrugInfoWithoutPhoto = fullDrugInfo.copy(drug = fullDrugInfo.drug.copy(photoPath = null))

            onReceive(fullDrugInfoWithoutPhoto)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private inner class SendThread(
        private val device: BluetoothDevice,
        private val fullDrugInfo: FullDrugInfo,
        private val afterSending: () -> Unit,
    ) : Thread() {

        override fun run() {
            Log.d(TAG, "Send: run")
            val socket = device.createRfcommSocketToServiceRecord(MY_UUID)
            try {
                Log.d(TAG, "Send: Connecting to socket")
                socket.connect()
                Log.d(TAG, "Send: Connected to socket")
            } catch (e: IOException) {
                try {
                    Log.e(TAG, "Send: could not connect to socket", e)
                    socket.close()
                } catch (closeException: IOException) {
                    Log.e(TAG, "Send: could not close the client socket", closeException)
                }
                return
            }

            val outStream = socket.outputStream

            val bytes = ProtoBuf.encodeToByteArray(fullDrugInfo)
            Log.d(TAG, "Sending bytes: $bytes")
            outStream.write(bytes)
            outStream.flush()
            sleep(10000)
            outStream.close()
            afterSending()
        }
    }
}

interface ActivityBluetoothController {
    fun makeDeviceDiscoverable()
    fun tryEnablingBluetooth()
}
