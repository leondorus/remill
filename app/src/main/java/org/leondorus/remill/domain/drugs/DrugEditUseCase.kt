package org.leondorus.remill.domain.drugs

import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId

class DrugEditUseCase(private val drugEditRepo: DrugEditRepo) {
    suspend fun updateDrug(drug: Drug): Boolean {
        return drugEditRepo.updateDrug(drug)
    }
    suspend fun deleteDrug(drugId: DrugId): Boolean {
        return drugEditRepo.deleteDrug(drugId)
    }

    // returns false when there was no such drug
    // returns true if there was such drug
}