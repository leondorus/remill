package org.leondorus.remill.domain.platfromnotifications

import org.leondorus.remill.domain.model.NotifTypes
import org.leondorus.remill.domain.model.PlatformNotification
import org.leondorus.remill.domain.model.PlatformNotificationId
import java.time.LocalDateTime

class PlatformNotificationEditUseCase(private val platformNotificationEditRepo: PlatformNotificationEditRepo) {
    suspend fun addPlatformNotification(dateTime: LocalDateTime, notifTypes: NotifTypes): PlatformNotification {
        return platformNotificationEditRepo.addPlatformNotification(dateTime, notifTypes)
    }
    suspend fun updatePlatformNotification(platformNotification: PlatformNotification) {
        platformNotificationEditRepo.updatePlatformNotification(platformNotification)
    }
    suspend fun deletePlatformNotification(id: PlatformNotificationId) {
        platformNotificationEditRepo.deletePlatformNotification(id)
    }
//    fun rescheduleAllNotifications() {
//      TODO (reschedule after boot, probably in org.leondorus.remill.alarms)
//    }
}