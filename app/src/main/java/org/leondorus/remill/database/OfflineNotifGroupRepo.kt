package org.leondorus.remill.database

import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.NotifGroup
import org.leondorus.remill.domain.model.NotifGroupId
import org.leondorus.remill.domain.model.UsePattern
import org.leondorus.remill.domain.notifgroups.NotifGroupEditRepo
import org.leondorus.remill.domain.notifgroups.NotifGroupGetRepo

class OfflineNotifGroupRepo(private val notifGroupDao: DbNotifGroupDao) : NotifGroupGetRepo,
    NotifGroupEditRepo {
    override suspend fun addNotifGroup(name: String, usePattern: UsePattern): NotifGroup {
        val dbUsePattern = DbUsePattern(
            push = DbNotifTypePush(usePattern.notifTypes.push.isActive),
            audio = DbNotifTypeAudio(usePattern.notifTypes.audio.isActive),
            flashlight = DbNotifTypeFlashlight(usePattern.notifTypes.flashlight.isActive),
            blinkingScreen = DbNotifTypeBlinkingScreen(usePattern.notifTypes.blinkingScreen.isActive),
        )
        val dbNotifGroup = DbNotifGroup(0, name, dbUsePattern)
        val notifGroupId = notifGroupDao.insertNotifGroup(dbNotifGroup)
        val dbNotifGroupTimes = usePattern.schedule.times.map { time ->
            DbNotifGroupTime(notifGroupId.toInt(), time)
        }
        notifGroupDao.insertNotifGroupTimes(dbNotifGroupTimes)

        val notifGroup = NotifGroup(NotifGroupId(notifGroupId.toInt()), name, usePattern, emptyList())
        return notifGroup
    }

    override suspend fun updateNotifGroup(newNotifGroup: NotifGroup) {
//        val dbNotifGroup = D
    }

    override suspend fun deleteNotifGroup(id: NotifGroupId) {
        TODO("Not yet implemented")
    }

    override fun getNotifGroup(id: NotifGroupId): Flow<NotifGroup> {
        TODO("Not yet implemented")
    }

    override fun getAllNotifGroups(): Flow<List<NotifGroup>> {
        TODO("Not yet implemented")
    }
}