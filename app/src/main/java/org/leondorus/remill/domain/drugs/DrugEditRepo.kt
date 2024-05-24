package org.leondorus.remill.domain.drugs

import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId

interface DrugEditRepo {
    suspend fun addDrug(name: String): Drug
    suspend fun updateDrug(drug: Drug)
    suspend fun deleteDrug(drugId: DrugId)
    // True if drug udpate or delete w
}
