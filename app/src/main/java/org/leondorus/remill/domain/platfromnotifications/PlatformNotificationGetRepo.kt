package org.leondorus.remill.domain.platfromnotifications

import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.PlatformNotification
import org.leondorus.remill.domain.model.PlatformNotificationId
import java.time.LocalDateTime

interface PlatformNotificationGetRepo {
    fun getPlatformNotification(id: PlatformNotificationId): Flow<PlatformNotification?>
    fun getAllPlatformNotification(): Flow<List<PlatformNotification>>
    fun getPlatformNotificationByTime(localDateTime: LocalDateTime): Flow<PlatformNotification?>
}