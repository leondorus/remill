package org.leondorus.remill

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import org.leondorus.remill.bluetooth.ActivityBluetoothController
import org.leondorus.remill.bluetooth.AndroidBluetoothWrapper
import org.leondorus.remill.ui.RemillApp
import org.leondorus.remill.ui.theme.RemillTheme

class MainActivity : ComponentActivity(), PermissionProvider, ActivityBluetoothController {
    private val requestPermissionLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}
    private val enablingBluetoothLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    private val makeDiscoverableLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    private val container: RemillContainer
        get() = (application as RemillApplication).container

    private lateinit var activityPermissionManager: ActivityPermissionManager
    private var androidBluetoothWrapper: AndroidBluetoothWrapper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityPermissionManager = container.permissionManager as ActivityPermissionManager
        androidBluetoothWrapper = container.androidBluetoothWrapper

        setContent {
            RemillTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    RemillApp()
                }
            }
        }

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        val receiver = container.androidBluetoothWrapper?.broadcastReceiver
        if (receiver != null) registerReceiver(receiver, filter)
    }

    override fun requestPermissions(permissions: Array<String>) {
        requestPermissionLauncher.launch(permissions)
    }

    override fun tryEnablingBluetooth() {
        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        enablingBluetoothLauncher.launch(intent)
    }

    override fun makeDeviceDiscoverable() {
        val discoverableIntent: Intent =
            Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
            }
        makeDiscoverableLauncher.launch(discoverableIntent)
    }

    override fun onStart() {
        super.onStart()
        activityPermissionManager.attachPermissionProvider(this)
        androidBluetoothWrapper?.attachActivityBluetoothController(this)
    }

    override fun onStop() {
        super.onStop()
        activityPermissionManager.detachPermissionProvider(this)
        androidBluetoothWrapper?.detachActivityBluetoothController(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        val receiver = container.androidBluetoothWrapper?.broadcastReceiver
        if (receiver != null) unregisterReceiver(receiver)
    }
}