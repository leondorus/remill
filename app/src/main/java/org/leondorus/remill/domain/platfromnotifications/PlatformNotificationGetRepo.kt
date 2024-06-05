package org.leondorus.remill.domain.platfromnotifications

import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.PlatformNotification
import org.leondorus.remill.domain.model.PlatformNotificationId

interface PlatformNotificationGetRepo {
    fun getPlatformNotification(id: PlatformNotificationId): Flow<PlatformNotification?>
    fun getAllPlatformNotification(): Flow<List<PlatformNotification>>
}