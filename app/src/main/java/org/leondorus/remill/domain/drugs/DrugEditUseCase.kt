package org.leondorus.remill.domain.drugs

import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId

class DrugEditUseCase(private val drugEditRepo: DrugEditRepo) {
    suspend fun addDrug(name: String): Drug {
        // TODO(add notifgroup by default)
        return drugEditRepo.addDrug(name)
    }
    suspend fun updateDrug(drug: Drug) {
        return drugEditRepo.updateDrug(drug)
    }
    suspend fun deleteDrug(drugId: DrugId) {
        return drugEditRepo.deleteDrug(drugId)
    }
}