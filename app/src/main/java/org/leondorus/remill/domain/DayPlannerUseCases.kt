package org.leondorus.remill.domain

import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.model.NotifType
import org.leondorus.remill.domain.model.UsePattern
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.SortedMap

private val startOfTheDay: LocalTime = LocalTime.of(4, 0)
class DayPlannerGetUseCase(
    private val dayPlannerGetRepo: DayPlannerGetRepo,
)
{

    fun getPlan(): Flow<Plan> {
        TODO()
    }
}

class DayPlannerEditUseCase(
    private val dayPlannerEditRepo: DayPlannerEditRepo,
    private val notifCreator: NotifCreator
) {
    suspend fun schedulePlanAndNotifsFor(localDate: LocalDate) {
        TODO()
    }
    suspend fun addDrugWithUsePattern(drugId: DrugId, usePattern: UsePattern) {
        TODO()
    }
    suspend fun deleteDrugWithUsePattern(drugId: DrugId, usePattern: UsePattern) {
        TODO()
    }
    suspend fun setCompletionInfo(planEntry: PlanEntry, completionInfo: CompletionInfo) {
        TODO()
    }
}

data class Plan(
    val uses: SortedMap<PlanEntry, CompletionInfo>
)

data class PlanEntry(
    val drugId: DrugId,
    val localDateTime: LocalDateTime,
)

data class CompletionInfo(
    val isCompleted: Boolean
)