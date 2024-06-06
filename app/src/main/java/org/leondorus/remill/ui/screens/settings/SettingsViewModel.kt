package org.leondorus.remill.ui.screens.settings

import android.Manifest
import android.os.Build
import androidx.lifecycle.ViewModel
import org.leondorus.remill.ActivityPermissionManager
import org.leondorus.remill.PermissionManager

class SettingsViewModel(private val permissionManager: PermissionManager): ViewModel() {
    fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            permissionManager.requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
    }
}