package org.leondorus.remill

interface PermissionManager {
    fun checkPermissionGranted(permission: String): Boolean
    fun requestPermissions(permissions: Array<String>)
}
