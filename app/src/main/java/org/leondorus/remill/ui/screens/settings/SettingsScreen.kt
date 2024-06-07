package org.leondorus.remill.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.leondorus.remill.R
import org.leondorus.remill.ui.AppViewModelProvider

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    Column(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Button(onClick = { viewModel.requestNotificationPermission() }) {
            Text(stringResource(R.string.request_notification_permissions))
        }
        Button(onClick = {viewModel.requestBluetoothPermissions()}) {
            Text(text = stringResource(R.string.request_bluetooth_permissions))
        }
        Button(onClick = {viewModel.requestCameraPermissions()}) {
            Text(text = stringResource(R.string.request_camera_permissions))
        }
    }
}