package org.leondorus.remill.domain

import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.model.NotifGroup
import org.leondorus.remill.domain.model.NotifGroupId
import org.leondorus.remill.domain.model.NotifType
import org.leondorus.remill.domain.model.Schedule
import org.leondorus.remill.domain.model.UsePattern

class NotifGroupGetUseCase(private val notifGroupGetRepo: NotifGroupGetRepo) {
    fun getNotifGroup(id: NotifGroupId): Flow<NotifGroup> = notifGroupGetRepo.getNotifGroup(id)
    fun getAllNotifGroupIds(): Flow<List<NotifGroupId>> = notifGroupGetRepo.getAllNotifGroupIds()
    fun getAllNotifGroups(): Flow<List<NotifGroup>> = notifGroupGetRepo.getAllNotifGroups()
}

class NotifGroupEditUseCase(private val drugEditRepo: DrugEditRepo) {
    suspend fun addNotifGroup(name: String): NotifGroupId {
        TODO()
    }

    suspend fun setNotifGroupName(id: NotifGroupId, name: String) {
        TODO()
    }

    suspend fun setUsePatterns(id: NotifGroupId, usePatterns: List<UsePattern>) {

    }

    suspend fun addDrugToNotifGroup(notifGroupId: NotifGroupId, drugId: DrugId) {
        TODO()
    }

    suspend fun deleteDrugFromNotifGroup(notifGroupId: NotifGroupId, drugId: DrugId) {
        TODO()
    }

    suspend fun deleteNotifGroup(id: NotifGroupId) {
        TODO()
    }
}
