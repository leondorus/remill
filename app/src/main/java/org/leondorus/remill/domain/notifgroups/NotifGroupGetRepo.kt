package org.leondorus.remill.domain.notifgroups

import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.NotifGroup
import org.leondorus.remill.domain.model.NotifGroupId

interface NotifGroupGetRepo {
    fun getNotifGroup(id: NotifGroupId): Flow<NotifGroup?>
    fun getAllNotifGroups(): Flow<List<NotifGroup>>
}