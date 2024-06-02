package org.leondorus.remill.domain.notifgroups

import org.leondorus.remill.domain.model.NotifGroup
import org.leondorus.remill.domain.model.NotifGroupId
import org.leondorus.remill.domain.model.UsePattern

class NotifGroupEditUseCase(private val notifGroupEditRepo: NotifGroupEditRepo) {
    suspend fun addNotifGroup(name: String, usePattern: UsePattern): NotifGroup {
        return notifGroupEditRepo.addNotifGroup(name, usePattern)
    }
    suspend fun updateNotifGroup(newNotifGroup: NotifGroup) {
        if (newNotifGroup.drugs.isNotEmpty())
            throw DrugListIsNotEmpty("while updating with $newNotifGroup")

        notifGroupEditRepo.updateNotifGroup(newNotifGroup)
    }
    suspend fun deleteNotifGroup(id: NotifGroupId) {
        notifGroupEditRepo.deleteNotifGroup(id)
    }
}