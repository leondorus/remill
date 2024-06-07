package org.leondorus.remill.ui.screens.sharing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.leondorus.remill.R
import org.leondorus.remill.ui.AppViewModelProvider

@Composable
fun SharingReceiveScreen(
    modifier: Modifier = Modifier,
    viewModel: SharingReceiveViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            SharingReceiveUiState.Initial -> {
                Button(onClick = { viewModel.startReceiving() }) {
                    Text(text = stringResource(R.string.sharing_receiving_start_receiving))
                }
                Spacer(modifier = Modifier.padding(4.dp))
                Button(onClick = { viewModel.makeDiscoverable() }) {
                    Text(text = stringResource(R.string.sharing_receiving_make_discoverable))
                }
            }
            SharingReceiveUiState.Connecting -> {
                DecoratedText(stringResource(R.string.sharing_receiving_connecting))
            }
            SharingReceiveUiState.Receiving -> {
                DecoratedText(stringResource(R.string.sharing_receiving_receiving))
            }
            is SharingReceiveUiState.UpdatingLocalDatabase -> {
                DecoratedText(stringResource(R.string.sharing_receiving_update_local_database))
            }
            SharingReceiveUiState.Done -> {
                DecoratedText(stringResource(R.string.sharing_receiving_done))
            }
            SharingReceiveUiState.NoBluetoothAvailable -> {
                DecoratedText(text = stringResource(R.string.sharing_receiving_no_bluetooth_available))
            }
        }
    }
}

@Composable
fun DecoratedText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = MaterialTheme.typography.displayMedium)
}