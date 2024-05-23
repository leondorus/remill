package org.leondorus.remill.domain.drugs

import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId

interface DrugGetRepo {
    fun getDrug(id: DrugId): Flow<Drug?>
    fun getAllDrugs(): Flow<List<Drug>>
}