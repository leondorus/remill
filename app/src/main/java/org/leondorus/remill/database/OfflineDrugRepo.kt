package org.leondorus.remill.database

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.leondorus.remill.domain.drugs.DrugEditRepo
import org.leondorus.remill.domain.drugs.DrugGetRepo
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.model.NotifGroupId

class OfflineDrugRepo(private val drugDao: DbDrugDao) : DrugGetRepo, DrugEditRepo {
    override suspend fun addDrug(name: String): Drug {
        val dbDrug = DbDrug(0, name, null, null)
        val id = DrugId(drugDao.insertDrug(dbDrug).toInt())
        val drug = Drug(id, name, null, null)

        return drug
    }

    override suspend fun updateDrug(drug: Drug) {
        val dbDrug = DbDrug(drug.id.id, drug.name, drug.photoPath.toString(), drug.notifGroupId?.id)
        drugDao.updateDrug(dbDrug)
    }

    override suspend fun deleteDrug(drugId: DrugId) {
        drugDao.deleteDrug(drugId.id)
    }

    override fun getDrug(id: DrugId): Flow<Drug?> {
        return drugDao.getDrug(id.id).map { dbDrug ->
            if (dbDrug == null) return@map null

            val drugId = DrugId(dbDrug.id)
            val name = dbDrug.name
            val uri = if (dbDrug.photoPath == null) null else Uri.parse(dbDrug.photoPath)
            val notifGroupId =
                if (dbDrug.notifGroupId == null) null else NotifGroupId(dbDrug.notifGroupId)

            Drug(drugId, name, uri, notifGroupId)
        }
    }

    override fun getAllDrugs(): Flow<List<Drug>> {
        return drugDao.getAllDrugs().map { dbDrugs ->
            dbDrugs.map { dbDrug ->
                val drugId = DrugId(dbDrug.id)
                val name = dbDrug.name
                val uri = if(dbDrug.photoPath == null) null else Uri.parse(dbDrug.photoPath)
                val notifGroupId =
                    if (dbDrug.notifGroupId == null) null else NotifGroupId(dbDrug.notifGroupId)

                Drug(drugId, name, uri, notifGroupId)
            }
        }
    }
}