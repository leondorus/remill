package org.leondorus.remill

import android.content.Context
import org.leondorus.remill.database.OfflineDrugRepo
import org.leondorus.remill.database.RemillDatabase
import org.leondorus.remill.domain.DrugEditUseCase
import org.leondorus.remill.domain.DrugGetUseCase

interface AppContainer {
    val drugGetUseCase: DrugGetUseCase
    val drugEditUseCase: DrugEditUseCase
//    val dayPlanGetUseCase: DayPlannerGetUseCase
//    val dayPlanEditUseCase: DayPlannerEditUseCase
//    val notifGroupGetUseCase: NotifGroupGetUseCase
//    val notifGroupEditUseCase: NotifGroupEditUseCase
}

class AppDataContainer(private val context: Context,
) : AppContainer {
    override val drugGetUseCase: DrugGetUseCase
    override val drugEditUseCase: DrugEditUseCase
    init {
        val offlineDrugRepo = OfflineDrugRepo(RemillDatabase.getDatabase(context).drugDao(), RemillDatabase.getDatabase(context).drugNotifGroupCrossRefDao())
        drugGetUseCase = DrugGetUseCase(offlineDrugRepo)
        drugEditUseCase = DrugEditUseCase(offlineDrugRepo)
    }
}