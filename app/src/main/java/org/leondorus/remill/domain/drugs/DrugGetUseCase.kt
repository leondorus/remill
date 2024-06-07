package org.leondorus.remill.domain.drugs

import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId

class DrugGetUseCase(private val drugGetRepo: DrugGetRepo) {
    fun getDrug(drugId: DrugId): Flow<Drug?> {
        return drugGetRepo.getDrug(drugId)
    }

    fun getAllDrugs(): Flow<List<Drug>> {
        return drugGetRepo.getAllDrugs()
    }
}