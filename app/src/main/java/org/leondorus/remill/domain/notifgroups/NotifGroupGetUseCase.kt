package org.leondorus.remill.domain.notifgroups

import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.NotifGroup
import org.leondorus.remill.domain.model.NotifGroupId

class NotifGroupGetUseCase(private val notifGroupGetRepo: NotifGroupGetRepo) {
    fun getNotifGroup(id: NotifGroupId): Flow<NotifGroup?> {
        return notifGroupGetRepo.getNotifGroup(id)
    }

    fun getAllNotifGroups(): Flow<List<NotifGroup>> {
        return notifGroupGetRepo.getAllNotifGroups()
    }
}