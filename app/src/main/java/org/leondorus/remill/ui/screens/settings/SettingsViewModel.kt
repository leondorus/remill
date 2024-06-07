package org.leondorus.remill.ui.screens.settings

import android.Manifest
import android.os.Build
import androidx.lifecycle.ViewModel
import org.leondorus.remill.PermissionManager

class SettingsViewModel(private val permissionManager: PermissionManager) : ViewModel() {
    fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            permissionManager.requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
    }

    fun requestBluetoothPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN)
        } else {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
        permissionManager.requestPermissions(permissions)
    }

    fun requestCameraPermissions() {
        val permissions = arrayOf(Manifest.permission.CAMERA)
        permissionManager.requestPermissions(permissions)
    }
}