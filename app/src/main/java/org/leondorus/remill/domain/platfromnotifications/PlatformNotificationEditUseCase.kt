package org.leondorus.remill.domain.platfromnotifications

import org.leondorus.remill.domain.model.NotifGroupId
import org.leondorus.remill.domain.model.NotifTypes
import org.leondorus.remill.domain.model.PlatformNotification
import org.leondorus.remill.domain.model.PlatformNotificationId
import java.time.LocalDateTime

class PlatformNotificationEditUseCase(private val platformNotificationEditRepo: PlatformNotificationEditRepo) {
    suspend fun addPlatformNotification(dateTime: LocalDateTime, notifTypes: NotifTypes, notifGroupId: NotifGroupId): PlatformNotification {
        return platformNotificationEditRepo.addPlatformNotification(dateTime, notifTypes, notifGroupId)
    }
    suspend fun updatePlatformNotification(platformNotification: PlatformNotification) {
        platformNotificationEditRepo.updatePlatformNotification(platformNotification)
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