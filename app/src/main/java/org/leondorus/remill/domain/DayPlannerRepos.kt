package org.leondorus.remill.domain

import kotlinx.coroutines.flow.Flow

interface DayPlannerGetRepo {
    fun getDayPlan(): Flow<Plan>
}

interface DayPlannerEditRepo {
    suspend fun setDayPlan(plan: Plan)
    suspend fun addPlanEntry(planEntry: PlanEntry)
    suspend fun deletePlanEntry(planEntry: PlanEntry)
    suspend fun setCompletionInfo(planEntry: PlanEntry, completionInfo: CompletionInfo)
}