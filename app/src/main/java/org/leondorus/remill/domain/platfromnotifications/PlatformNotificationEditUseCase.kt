package org.leondorus.remill.domain.platfromnotifications

import org.leondorus.remill.domain.model.NotifGroupId
import org.leondorus.remill.domain.model.NotifTypes
import org.leondorus.remill.domain.model.PlatformNotification
import org.leondorus.remill.domain.model.PlatformNotificationId
import java.time.LocalDateTime

class PlatformNotificationEditUseCase(private val platformNotificationEditRepo: PlatformNotificationEditRepo) {
    suspend fun addPlatformNotification(
        dateTime: LocalDateTime,
        notifTypes: NotifTypes,
        notifGroupId: NotifGroupId,
    ): PlatformNotification? {
        val currentDateTime = LocalDateTime.now()
        var platformNotification: PlatformNotification? = null
        if (currentDateTime < dateTime) {
            platformNotification = platformNotificationEditRepo.addPlatformNotification(
                dateTime,
                notifTypes,
                notifGroupId
            )
        }
        return platformNotification
    }

    suspend fun updatePlatformNotification(platformNotification: PlatformNotification): Boolean {
        val currentDateTime = LocalDateTime.now()
        if (currentDateTime < platformNotification.dateTime) {
            platformNotificationEditRepo.updatePlatformNotification(platformNotification)
            return true
        }
        platformNotificationEditRepo.deletePlatformNotification(platformNotification.id)
        return false
    }

    suspend fun deletePlatformNotification(id: PlatformNotificationId) {
        platformNotificationEditRepo.deletePlatformNotification(id)
    }

    suspend fun deleteAllPlatformNotificationsWithNotifGroup(notifGroupId: NotifGroupId) {
        platformNotificationEditRepo.deleteAllPlatformNotificationsWithNotifGroup(notifGroupId)
    }
//    fun rescheduleAllNotifications() {
//      TODO (reschedule after boot, probably in org.leondorus.remill.alarms)
//    }
}