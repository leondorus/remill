package org.leondorus.remill.domain.sharing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.leondorus.remill.domain.drugs.DrugEditUseCase
import org.leondorus.remill.domain.drugs.DrugGetUseCase
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.notifgroups.NotifGroupEditUseCase
import org.leondorus.remill.domain.notifgroups.NotifGroupGetUseCase
import org.leondorus.remill.domain.platfromnotifications.PlatformNotificationEditUseCase
import java.time.LocalDateTime

class FullDrugInfoUseCase(
    private val drugEditUseCase: DrugEditUseCase,
    private val drugGetUseCase: DrugGetUseCase,
    private val notifGroupEditUseCase: NotifGroupEditUseCase,
    private val notifGroupGetUseCase: NotifGroupGetUseCase,
    private val platformNotificationEditUseCase: PlatformNotificationEditUseCase,
) {
    suspend fun addFullDrug(fullDrugInfo: FullDrugInfo) {
        val initDrug = fullDrugInfo.drug
        val initNotifGroup = fullDrugInfo.notifGroup
        val newNotifGroup =
            if (initNotifGroup == null) null else notifGroupEditUseCase.addNotifGroup(
                initNotifGroup.name, initNotifGroup.usePattern
            )
        val newDrug = drugEditUseCase.addDrug(initDrug.name).copy(notifGroupId = newNotifGroup?.id)
        drugEditUseCase.updateDrug(newDrug)
        if (newNotifGroup != null) {
            val curTime = LocalDateTime.now()
            for (time in newNotifGroup.usePattern.schedule.times) {
                if (curTime < time) {
                    platformNotificationEditUseCase.addPlatformNotification(
                        time, newNotifGroup.usePattern.notifTypes, newNotifGroup.id
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getFullDrugInfo(drugId: DrugId): Flow<FullDrugInfo?> {
        val resFlow = drugGetUseCase.getDrug(drugId).flatMapLatest { drug ->
            if (drug == null) {
                return@flatMapLatest flow<FullDrugInfo?> {
                    emit(null)
                }
            }
            val notifGroupId = drug.notifGroupId ?: return@flatMapLatest flow<FullDrugInfo> {
                FullDrugInfo(
                    drug,
                    null
                )
            }
            val resFlow = notifGroupGetUseCase.getNotifGroup(notifGroupId).map { notifGroup ->
                FullDrugInfo(drug, notifGroup)
            }
            resFlow
        }

        return resFlow
    }
}