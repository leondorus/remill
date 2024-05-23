package org.leondorus.remill.domain.drugs

import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId

interface DrugEditRepo {
    suspend fun updateDrug(drug: Drug): Boolean // True if drug existed
    suspend fun deleteDrug(drugId: DrugId): Boolean
}