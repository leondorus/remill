package org.leondorus.remill.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.model.NotifGroupId

class DrugGetUseCase(private val drugGetRepo: DrugGetRepo) {
    fun getDrug(id: DrugId): Flow<Drug> = drugGetRepo.getDrug(id)
    fun getAllDrugs(): Flow<List<Drug>> = drugGetRepo.getAllDrugs()
}

class DrugEditUseCase(
    private val drugEditRepo: DrugEditRepo,
//    private val notifGroupGetUseCase: NotifGroupGetUseCase,
//    private val dayPlannerEditUseCase: DayPlannerEditUseCase,
) {
    suspend fun addDrug(name: String, note: String): DrugId = drugEditRepo.addDrug(name, note)
    suspend fun updateDrug(drug: Drug) = drugEditRepo.updateDrug(drug)
    suspend fun addNotifGroupToDrug(drugId: DrugId, notifGroupId: NotifGroupId) {
        drugEditRepo.addNotifGroupToDrug(drugId, notifGroupId)
//        val notifGroup = notifGroupGetUseCase.getNotifGroup(notifGroupId).first()
//        for (usePattern in notifGroup.usePatterns) {
//            dayPlannerEditUseCase.addDrugWithUsePattern(drugId, usePattern)
//        }
    }
    suspend fun deleteNotifGroupFromDrug(drugId: DrugId, notifGroupId: NotifGroupId) {
        drugEditRepo.deleteNotifGroupFromDrug(drugId, notifGroupId)
//        val notifGroup = notifGroupGetUseCase.getNotifGroup(notifGroupId).first()
//        for (usePattern in notifGroup.usePatterns) {
//            dayPlannerEditUseCase.deleteDrugWithUsePattern(drugId, usePattern)
//        }
    }

    suspend fun deleteDrug(id: DrugId) = drugEditRepo.deleteDrug(id)
}