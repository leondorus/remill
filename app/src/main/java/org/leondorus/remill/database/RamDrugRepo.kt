package org.leondorus.remill.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.leondorus.remill.domain.drugs.DrugEditRepo
import org.leondorus.remill.domain.drugs.DrugGetRepo
import org.leondorus.remill.domain.drugs.NoDrugWithSuchId
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId

class RamDrugRepo(private val coroutineScope: CoroutineScope): DrugGetRepo, DrugEditRepo {
    private val dataMap = mutableMapOf<DrugId, Drug>()
    private val drugObservers = mutableMapOf<DrugId, MutableSharedFlow<Drug?>>()
    private val allDrugs = MutableSharedFlow<List<Drug>>()

    private suspend fun putAndEmit(id: DrugId, drug: Drug) {
        dataMap[id] = drug
        drugObservers[id]?.emit(drug)
        allDrugs.emit(dataMap.values.toList())
    }

    override fun getDrug(id: DrugId): Flow<Drug?> {
        val mutableSharedFlow = drugObservers.getOrPut(id) {
            MutableSharedFlow()
        }
        val friend = dataMap[id]
        coroutineScope.launch {
            mutableSharedFlow.emit(friend)
        }

        return mutableSharedFlow.distinctUntilChanged()
    }

    override fun getAllDrugs(): Flow<List<Drug>> {
        return allDrugs
    }

    override suspend fun addDrug(name: String): Drug {
        var newId = DrugId(0)
        for (i in 0..Int.MAX_VALUE) {
            if (!dataMap.contains(DrugId(i))) {
                newId = DrugId(i)
                break
            }
        }

        val drug = Drug(newId, name, null)
        putAndEmit(newId, drug)
        return drug
    }

    override suspend fun updateDrug(drug: Drug) {
        val id = drug.id
        val existed = dataMap[id] != null
        if (!existed) {
            throw NoDrugWithSuchId()
        }

        putAndEmit(id, drug)
    }

    override suspend fun deleteDrug(drugId: DrugId) {
        val existed = dataMap.remove(drugId) != null
        if (!existed) {
            throw NoDrugWithSuchId()
        }

        drugObservers[drugId]?.emit(null)
        allDrugs.emit(dataMap.values.toList())
    }
}
