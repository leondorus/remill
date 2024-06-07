package org.leondorus.remill

import android.bluetooth.BluetoothManager
import android.content.Context
import org.leondorus.remill.alarms.AndroidAlarmRepo
import org.leondorus.remill.alarms.AndroidAlarmSchedulerImpl
import org.leondorus.remill.bluetooth.AndroidBluetoothWrapper
import org.leondorus.remill.database.OfflineDrugRepo
import org.leondorus.remill.database.OfflineNotifGroupRepo
import org.leondorus.remill.database.RemillDatabase
import org.leondorus.remill.domain.drugs.DrugEditUseCase
import org.leondorus.remill.domain.drugs.DrugGetUseCase
import org.leondorus.remill.domain.notifgroups.NotifGroupEditUseCase
import org.leondorus.remill.domain.notifgroups.NotifGroupGetUseCase
import org.leondorus.remill.domain.platfromnotifications.PlatformNotificationEditUseCase
import org.leondorus.remill.domain.sharing.FullDrugInfoUseCase

interface RemillContainer {
    val drugGetUseCase: DrugGetUseCase
    val drugEditUseCase: DrugEditUseCase
    val notifGroupGetUseCase: NotifGroupGetUseCase
    val notifGroupEditUseCase: NotifGroupEditUseCase
    val fullDrugIntoUseCase: FullDrugInfoUseCase
    val permissionManager: PermissionManager
    val androidBluetoothWrapper: AndroidBluetoothWrapper?
}

class AndroidRemillContainer(context: Context) : RemillContainer {
    override val drugGetUseCase: DrugGetUseCase
    override val drugEditUseCase: DrugEditUseCase
    override val notifGroupGetUseCase: NotifGroupGetUseCase
    override val notifGroupEditUseCase: NotifGroupEditUseCase
    override val fullDrugIntoUseCase: FullDrugInfoUseCase
    override val permissionManager: PermissionManager = ActivityPermissionManager(context)
    override val androidBluetoothWrapper: AndroidBluetoothWrapper? = context.getSystemService(BluetoothManager::class.java).let { manager ->
        val adapter = manager.adapter ?: return@let null
        AndroidBluetoothWrapper(adapter)
    }

    init {
        val database = RemillDatabase.getDatabase(context)
        val fullDrugRepo = OfflineDrugRepo(database.drugDao())
        val fullNotifGroupRepo = OfflineNotifGroupRepo(database.notifGroupDao())
        val alarmScheduler = AndroidAlarmSchedulerImpl(context)
        val fullPlatformNotificationRepo =
            AndroidAlarmRepo(database.platformNotificationDao(), alarmScheduler)
        val platformNotificationEditUseCase = PlatformNotificationEditUseCase(fullPlatformNotificationRepo)

        drugGetUseCase = DrugGetUseCase(fullDrugRepo)
        drugEditUseCase = DrugEditUseCase(fullDrugRepo)
        notifGroupGetUseCase = NotifGroupGetUseCase(fullNotifGroupRepo)
        notifGroupEditUseCase = NotifGroupEditUseCase(fullNotifGroupRepo, platformNotificationEditUseCase)
        fullDrugIntoUseCase = FullDrugInfoUseCase(drugEditUseCase, drugGetUseCase, notifGroupEditUseCase, notifGroupGetUseCase, platformNotificationEditUseCase)
    }
}