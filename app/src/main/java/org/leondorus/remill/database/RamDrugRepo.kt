package org.leondorus.remill.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.leondorus.remill.domain.drugs.DrugEditRepo
import org.leondorus.remill.domain.drugs.DrugGetRepo
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId

class RamDrugRepo(private val coroutineScope: CoroutineScope): DrugGetRepo, DrugEditRepo {
    private val dataMap = mutableMapOf<DrugId, Drug>()
    private val drugObservers = mutableMapOf<DrugId, MutableSharedFlow<Drug?>>()
    private val allDrugs = MutableSharedFlow<List<Drug>>()
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

    override suspend fun updateDrug(drug: Drug): Boolean {
        val id = drug.id
        val existed = dataMap[id] != null

        dataMap[id] = drug
        drugObservers[id]?.emit(drug)
        allDrugs.emit(dataMap.values.toList())

        return existed
    }

    override suspend fun deleteDrug(drugId: DrugId): Boolean {
        val existed = dataMap.remove(drugId) != null
        drugObservers[drugId]?.emit(null)
        allDrugs.emit(dataMap.values.toList())

        return existed
    }
}
