package org.leondorus.remill.domain.platfromnotifications

import org.leondorus.remill.domain.model.NotifTypes
import org.leondorus.remill.domain.model.PlatformNotification
import org.leondorus.remill.domain.model.PlatformNotificationId
import java.time.LocalDateTime

interface PlatformNotificationEditRepo {
    suspend fun addPlatformNotification(dateTime: LocalDateTime, notifTypes: NotifTypes): PlatformNotification
    suspend fun updatePlatformNotification(platformNotification: PlatformNotification)
    suspend fun deletePlatformNotification(id: PlatformNotificationId)
}