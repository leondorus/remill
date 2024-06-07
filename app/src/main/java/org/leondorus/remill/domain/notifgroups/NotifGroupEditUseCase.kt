package org.leondorus.remill.domain.notifgroups

import org.leondorus.remill.domain.model.NotifGroup
import org.leondorus.remill.domain.model.NotifGroupId
import org.leondorus.remill.domain.model.UsePattern
import org.leondorus.remill.domain.platfromnotifications.PlatformNotificationEditUseCase

class NotifGroupEditUseCase(
    private val notifGroupEditRepo: NotifGroupEditRepo,
    private val platformNotificationEditUseCase: PlatformNotificationEditUseCase,
) {
    suspend fun addNotifGroup(name: String, usePattern: UsePattern): NotifGroup {
        val notifGroup = notifGroupEditRepo.addNotifGroup(name, usePattern)
        val notifTypes = notifGroup.usePattern.notifTypes
        for (time in notifGroup.usePattern.schedule.times) {
            platformNotificationEditUseCase.addPlatformNotification(time, notifTypes, notifGroup.id)
        }

        return notifGroup
    }

    suspend fun updateNotifGroup(newNotifGroup: NotifGroup) {
        if (newNotifGroup.drugs.isNotEmpty())
            throw DrugListIsNotEmpty("while updating with $newNotifGroup")

        notifGroupEditRepo.updateNotifGroup(newNotifGroup)
        platformNotificationEditUseCase.deleteAllPlatformNotificationsWithNotifGroup(newNotifGroup.id)
        for (time in newNotifGroup.usePattern.schedule.times) {
            platformNotificationEditUseCase.addPlatformNotification(
                time,
                newNotifGroup.usePattern.notifTypes,
                newNotifGroup.id
            )
        }
    }

    suspend fun deleteNotifGroup(id: NotifGroupId) {
        notifGroupEditRepo.deleteNotifGroup(id)
        platformNotificationEditUseCase.deleteAllPlatformNotificationsWithNotifGroup(id)
    }
}