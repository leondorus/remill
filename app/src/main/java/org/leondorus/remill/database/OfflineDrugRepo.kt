package org.leondorus.remill.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.leondorus.remill.domain.DrugEditRepo
import org.leondorus.remill.domain.DrugGetRepo
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.model.NotifGroupId

class OfflineDrugRepo(
    private val drugDao: DrugDao,
    private val drugNotifGroupCrossRefDao: DrugNotifGroupCrossRefDao,
) : DrugGetRepo, DrugEditRepo {
    override fun getDrug(id: DrugId): Flow<Drug> =
        drugDao.getDrugWithNotifGroups(id.id).map { dbDrugWithNotifGroups ->
            val dbDrug = dbDrugWithNotifGroups.dbDrug
            val notifGroupIds =
                dbDrugWithNotifGroups.dbNotifGroups.map { NotifGroupId(it.notifGroupId) }
            Drug(DrugId(dbDrug.drugId), dbDrug.name, dbDrug.note, notifGroupIds)
        }

    override fun getAllDrugIds(): Flow<List<DrugId>> = drugDao.getAllDrugIds().map { intList ->
        intList.map { int -> DrugId(int) }
    }

    override fun getAllDrugs(): Flow<List<Drug>> {
        val drugFlow = drugDao.getAllDrugsWithNotifGroups().map { bigList ->
            bigList.map { dbDrugWithNotifGroup ->
                val dbDrug = dbDrugWithNotifGroup.dbDrug
                val notifGroupIds =
                    dbDrugWithNotifGroup.dbNotifGroups.map { notifGroup -> NotifGroupId(notifGroup.notifGroupId) }
                Drug(DrugId(dbDrug.drugId), dbDrug.name, dbDrug.note, notifGroupIds)
            }
        }
        return drugFlow
    }

    override suspend fun addDrug(name: String, note: String): DrugId =
        DrugId(drugDao.insertDrugs(DbDrug(0, name, note))[0].toInt())

    override suspend fun updateDrug(newDrug: Drug) =
        drugDao.updateDrugs(DbDrug(newDrug.id.id, newDrug.name, newDrug.note))

    override suspend fun addNotifGroupToDrug(drugId: DrugId, notifGroupId: NotifGroupId) =
        drugNotifGroupCrossRefDao.insertDrugNotifGroupCrossRef(
            DrugNotifGroupCrossRef(
                drugId.id, notifGroupId.id
            )
        )

    override suspend fun deleteNotifGroupFromDrug(drugId: DrugId, notifGroupId: NotifGroupId) =
        drugNotifGroupCrossRefDao.deleteDrugNotifGroupCrossRef(
            DrugNotifGroupCrossRef(
                drugId.id, notifGroupId.id
            )
        )

    override suspend fun deleteDrug(id: DrugId) = drugDao.deleteDrugs(DbDrug(id.id, "", ""))
}