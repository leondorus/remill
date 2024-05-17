package org.leondorus.remill.domain.drugs

import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId

class DrugGetUseCase {
    fun getDrug(id: DrugId): Flow<Drug> {
        TODO()
    }
    fun getAllDrugs(): Flow<List<Drug>> {
        TODO()
    }
}