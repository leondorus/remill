package org.leondorus.remill

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class ActivityPermissionManager(private val context: Context) : PermissionManager {
    private var permissionProvider: PermissionProvider? = null

    override fun checkPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun requestPermissions(permissions: Array<String>) {
        if (permissionProvider == null) return

        val neededPermissions: MutableList<String> = mutableListOf()

        for (permission in permissions) {
            if (!checkPermissionGranted(permission)) neededPermissions.add(permission)
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
        }

        if (neededPermissions.isNotEmpty()) {
            permissionProvider?.requestPermissions(permissions)
        }
    }

    fun attachPermissionProvider(permissionProvider: PermissionProvider) {
        this.permissionProvider = permissionProvider
    }

    fun detachPermissionProvider(permissionProvider: PermissionProvider) {
        if (this.permissionProvider == permissionProvider) {
            this.permissionProvider = null
        }
    }
}

interface PermissionProvider {
    fun requestPermissions(permissions: Array<String>)
}