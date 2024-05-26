package org.leondorus.remill.domain

import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.model.NotifGroupId

interface DrugGetRepo {
    fun getDrug(id: DrugId): Flow<Drug>
    fun getAllDrugIds(): Flow<List<DrugId>>
    fun getAllDrugs(): Flow<List<Drug>>
}

interface DrugEditRepo {
    suspend fun addDrug(name: String, note: String): DrugId
    suspend fun updateDrug(newDrug: Drug)
    suspend fun addNotifGroupToDrug(drugId: DrugId, notifGroupId: NotifGroupId)
    suspend fun deleteNotifGroupFromDrug(drugId: DrugId, notifGroupId: NotifGroupId)
    suspend fun deleteDrug(id: DrugId)
}