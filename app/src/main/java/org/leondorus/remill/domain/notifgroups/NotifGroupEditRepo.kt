package org.leondorus.remill.domain.notifgroups

import org.leondorus.remill.domain.model.NotifGroup
import org.leondorus.remill.domain.model.NotifGroupId
import org.leondorus.remill.domain.model.UsePattern

interface NotifGroupEditRepo {
    suspend fun addNotifGroup(name: String, usePattern: UsePattern): NotifGroup
    suspend fun updateNotifGroup(newNotifGroup: NotifGroup)
    suspend fun deleteNotifGroup(id: NotifGroupId)
}