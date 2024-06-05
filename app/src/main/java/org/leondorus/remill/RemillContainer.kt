package org.leondorus.remill

import android.content.Context
import org.leondorus.remill.database.OfflineDrugRepo
import org.leondorus.remill.database.OfflineNotifGroupRepo
import org.leondorus.remill.database.RemillDatabase
import org.leondorus.remill.domain.drugs.DrugEditUseCase
import org.leondorus.remill.domain.drugs.DrugGetUseCase
import org.leondorus.remill.domain.notifgroups.NotifGroupEditUseCase
import org.leondorus.remill.domain.notifgroups.NotifGroupGetUseCase

interface RemillContainer {
    val drugGetUseCase: DrugGetUseCase
    val drugEditUseCase: DrugEditUseCase
    val notifGroupGetUseCase: NotifGroupGetUseCase
    val notifGroupEditUseCase: NotifGroupEditUseCase
    val permissionManager: PermissionManager
}

class AndroidRemillContainer(context: Context): RemillContainer {
    override val drugGetUseCase: DrugGetUseCase
    override val drugEditUseCase: DrugEditUseCase
    override val notifGroupGetUseCase: NotifGroupGetUseCase
    override val notifGroupEditUseCase: NotifGroupEditUseCase
    override val permissionManager: PermissionManager = ActivityPermissionManager(context)

    init {
        val database = RemillDatabase.getDatabase(context)
        val fullDrugRepo = OfflineDrugRepo(database.drugDao())
        val fullNotifGroupRepo = OfflineNotifGroupRepo(database.notifGroupDao())

        drugGetUseCase = DrugGetUseCase(fullDrugRepo)
        drugEditUseCase = DrugEditUseCase(fullDrugRepo)
        notifGroupGetUseCase = NotifGroupGetUseCase(fullNotifGroupRepo)
        notifGroupEditUseCase = NotifGroupEditUseCase(fullNotifGroupRepo)
    }
}