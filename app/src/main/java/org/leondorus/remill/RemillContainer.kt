package org.leondorus.remill

import android.content.Context
import kotlinx.coroutines.MainScope
import org.leondorus.remill.database.RamDrugRepo
import org.leondorus.remill.domain.drugs.DrugEditUseCase
import org.leondorus.remill.domain.drugs.DrugGetUseCase
import org.leondorus.remill.domain.notifgroups.NotifGroupEditUseCase
import org.leondorus.remill.domain.notifgroups.NotifGroupGetUseCase

interface RemillContainer {
    val drugGetUseCase: DrugGetUseCase
    val drugEditUseCase: DrugEditUseCase
    val notifGroupGetUseCase: NotifGroupGetUseCase
    val notifGroupEditUseCase: NotifGroupEditUseCase
}

class AndroidRemillContainer(context: Context): RemillContainer {
    override val drugGetUseCase: DrugGetUseCase
    override val drugEditUseCase: DrugEditUseCase
    init {
        val fullDrugRepo = RamDrugRepo(MainScope())
        drugGetUseCase = DrugGetUseCase(fullDrugRepo)
        drugEditUseCase = DrugEditUseCase(fullDrugRepo)
    }
}