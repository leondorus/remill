package org.leondorus.remill.domain

import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.model.NotifGroup
import org.leondorus.remill.domain.model.NotifGroupId
import org.leondorus.remill.domain.model.NotifType
import org.leondorus.remill.domain.model.Schedule
import org.leondorus.remill.domain.model.UsePattern

interface NotifGroupGetRepo {
    fun getNotifGroup(id: NotifGroupId): Flow<NotifGroup>
    fun getAllNotifGroupIds(): Flow<List<NotifGroupId>>
    fun getAllNotifGroups(): Flow<List<NotifGroup>>
}
interface NotifGroupEditRepo {
    suspend fun addNotifGroup(name: String): NotifGroupId
    suspend fun setNotifGroupName(id: NotifGroupId, name: String)
    suspend fun setUsePatterns(id: NotifGroupId, usePatterns: List<UsePattern>)
    suspend fun addDrugToNotifGroup(notifGroupId: NotifGroupId, drugId: DrugId)
    suspend fun deleteNotifGroup(id: NotifGroupId)
}