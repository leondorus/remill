package org.leondorus.remill.ui.screens.sharing

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.leondorus.remill.ui.AppViewModelProvider

@Composable
fun SharingSendScreen(
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SharingSendViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by viewModel.uiState.collectAsState()
    val bluetoothDevices by viewModel.devices.collectAsState()

    when (uiState) {
        SharingSendUiState.Initial -> {
            LazyColumn(
                modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(bluetoothDevices) { bluetoothDevice ->
                    BluetoothDeviceCard(onClick = {
                        viewModel.startSending(bluetoothDevice)
                    }, bluetoothDevice = bluetoothDevice)
                }
            }
        }
        SharingSendUiState.Sending -> {Text("Sending")}
        SharingSendUiState.Done -> {Text("Done")}
    }
}

@SuppressLint("MissingPermission")
@Composable
fun BluetoothDeviceCard(
    onClick: () -> Unit,
    bluetoothDevice: BluetoothDevice,
    modifier: Modifier = Modifier,
) {
    val name = bluetoothDevice.name ?: ""
    Card(modifier = modifier.fillMaxWidth().clickable { onClick() }) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(bluetoothDevice.address, style = MaterialTheme.typography.bodySmall)
        }
    }
}