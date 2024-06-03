package org.leondorus.remill.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.model.NotifGroup
import org.leondorus.remill.domain.model.NotifGroupId
import org.leondorus.remill.domain.model.NotifType
import org.leondorus.remill.domain.model.NotifTypes
import org.leondorus.remill.domain.model.Schedule
import org.leondorus.remill.domain.model.UsePattern
import org.leondorus.remill.domain.notifgroups.NotifGroupEditRepo
import org.leondorus.remill.domain.notifgroups.NotifGroupGetRepo

private const val TAG = "OfflineNotifGroupRepo"

class OfflineNotifGroupRepo(private val notifGroupDao: DbNotifGroupDao) : NotifGroupGetRepo,
    NotifGroupEditRepo {
    private fun usePatternToDb(usePattern: UsePattern): DbUsePattern {
        val dbUsePattern = DbUsePattern(
            push = DbNotifTypePush(usePattern.notifTypes.push.isActive),
            audio = DbNotifTypeAudio(usePattern.notifTypes.audio.isActive),
            flashlight = DbNotifTypeFlashlight(usePattern.notifTypes.flashlight.isActive),
            blinkingScreen = DbNotifTypeBlinkingScreen(usePattern.notifTypes.blinkingScreen.isActive),
        )

        return dbUsePattern
    }

    private fun usePatternToDbTimes(usePattern: UsePattern, notifGroupId: NotifGroupId): List<DbNotifGroupTime> {
        val dbTimes = usePattern.schedule.times.map {  ldt ->
            DbNotifGroupTime(notifGroupId.id, ldt)
        }
        return dbTimes
    }

    private fun dbToUsePattern(
        times: List<DbNotifGroupTime>,
        dbUsePattern: DbUsePattern,
    ): UsePattern {
        val notifTypes = NotifTypes(
            NotifType.Push(dbUsePattern.push.isActive),
            NotifType.Audio(dbUsePattern.audio.isActive),
            NotifType.Flashlight(dbUsePattern.flashlight.isActive),
            NotifType.BlinkingScreen(dbUsePattern.blinkingScreen.isActive),
        )
        val justTimes = times.map {
            it.dateTime
        }
        val usePattern = UsePattern(Schedule(justTimes), notifTypes)
        return usePattern
    }

    override suspend fun addNotifGroup(name: String, usePattern: UsePattern): NotifGroup {
        val dbUsePattern = usePatternToDb(usePattern)
        val dbNotifGroup = DbNotifGroup(0, name, dbUsePattern)
        val notifGroupId = NotifGroupId(notifGroupDao.insertNotifGroup(dbNotifGroup).toInt())

        val dbNotifGroupTimes = usePatternToDbTimes(usePattern, notifGroupId)

        notifGroupDao.insertNotifGroupTimes(dbNotifGroupTimes)

        val notifGroup = NotifGroup(notifGroupId, name, usePattern, emptyList())
        return notifGroup
    }

    override suspend fun updateNotifGroup(newNotifGroup: NotifGroup) {
        val id = newNotifGroup.id.id
        val name = newNotifGroup.name
        val dbUsePattern = usePatternToDb(newNotifGroup.usePattern)

        val dbNotifGroup = DbNotifGroup(id, name, dbUsePattern)
        notifGroupDao.updateNotifGroup(dbNotifGroup)

        notifGroupDao.deleteAllTimesFromNotifGroup(id)
        val times = usePatternToDbTimes(newNotifGroup.usePattern, newNotifGroup.id)
        notifGroupDao.insertNotifGroupTimes(times)
    }

    override suspend fun deleteNotifGroup(id: NotifGroupId) {
        notifGroupDao.deleteAllTimesFromNotifGroup(id.id)
        notifGroupDao.deleteNotifGroup(id.id)
    }

    override fun getNotifGroup(id: NotifGroupId): Flow<NotifGroup?> {
        val flowWithDrugs = notifGroupDao.getNotifGroupWithDrugs(id.id)
        val flowWithTimes = notifGroupDao.getNotifGroupWithTimes(id.id)
        return combine(flowWithTimes, flowWithDrugs) { mapWithTimes, mapWithDrugs ->
            val dbNotifGroup = mapWithTimes.keys.firstOrNull() ?: return@combine null
            mapWithDrugs.keys.firstOrNull() ?: return@combine null
            // TODO(they may be different)

            val drugIds = mapWithDrugs.values.first().map { DrugId(it) }
            val times = mapWithTimes.values.first()

            val usePattern = dbToUsePattern(times, dbNotifGroup.usePattern)
            val notifGroup = NotifGroup(NotifGroupId(dbNotifGroup.id), dbNotifGroup.name, usePattern, drugIds)

            notifGroup
        }
    }

    override fun getAllNotifGroups(): Flow<List<NotifGroup>> {
        val flowWithDrugs = notifGroupDao.getAllNotifGroupsWithDrugs()
        val flowWithTimes = notifGroupDao.getAllNotifGroupsWithTimes()
        return combine(flowWithTimes, flowWithDrugs) { mapWithTimes, mapWithDrugs ->
            val timeMapIds = mapWithTimes.keys.map { it.id }.toSet()
            val drugMapIds = mapWithDrugs.keys.map { it.id }.toSet()
            val commonIds = timeMapIds.intersect(drugMapIds)

            val idMapWithTimes = mapWithTimes.filter { it.key.id in commonIds }.keys.associate { dbNotifGroup ->
                val id = dbNotifGroup.id
                val times = mapWithTimes[dbNotifGroup]!!

                id to (dbNotifGroup to times)
            }
            val idMapWithDrugs = mapWithDrugs.filter { it.key.id in commonIds }.keys.associate { dbNotifGroup ->
                val id = dbNotifGroup.id
                val drugs = mapWithDrugs[dbNotifGroup]!!

                id to (dbNotifGroup to drugs)
            }
            val combinedMap = idMapWithDrugs.keys.intersect(idMapWithTimes.keys).associateWith { id ->
                val dbNotifGroup = idMapWithTimes[id]!!.first 
                val times = idMapWithTimes[id]!!.second
                val drugs = idMapWithDrugs[id]!!.second
                Triple(dbNotifGroup, times, drugs)
            }

            val notifGroups = combinedMap.map { entry ->
                val dbNotifGroup = entry.value.first
                val times = entry.value.second
                val drugIds = entry.value.third.map {DrugId(it)}

                val usePattern = dbToUsePattern(times, dbNotifGroup.usePattern)

                val notifGroupId = NotifGroupId(entry.key)
                val notifGroup = NotifGroup(notifGroupId, dbNotifGroup.name, usePattern, drugIds)
                notifGroup
            }
            
            notifGroups
        }
    }
}