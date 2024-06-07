package org.leondorus.remill.alarms

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.leondorus.remill.database.DbPlatformNotification
import org.leondorus.remill.database.DbPlatformNotificationDao
import org.leondorus.remill.database.toDbNotifTypes
import org.leondorus.remill.database.toDbPlatformNotification
import org.leondorus.remill.domain.model.NotifGroupId
import org.leondorus.remill.domain.model.NotifTypes
import org.leondorus.remill.domain.model.PlatformNotification
import org.leondorus.remill.domain.model.PlatformNotificationId
import org.leondorus.remill.domain.platfromnotifications.PlatformNotificationEditRepo
import org.leondorus.remill.domain.platfromnotifications.PlatformNotificationGetRepo
import java.time.LocalDateTime

class AndroidAlarmRepo(
    private val dbPlatformNotificationDao: DbPlatformNotificationDao,
    private val androidAlarmScheduler: AndroidAlarmScheduler,
) : PlatformNotificationGetRepo, PlatformNotificationEditRepo {
    override suspend fun addPlatformNotification(
        dateTime: LocalDateTime,
        notifTypes: NotifTypes,
        notifGroupId: NotifGroupId,
    ): PlatformNotification {
        val dbPlatformNotification =
            DbPlatformNotification(
                dateTime = dateTime,
                notifTypes = notifTypes.toDbNotifTypes(),
                notifGroupId = notifGroupId.id
            )
        val id = PlatformNotificationId(
            dbPlatformNotificationDao.insertPlatformNotification(dbPlatformNotification).toInt()
        )
        androidAlarmScheduler.addAlarm(id, dateTime, notifTypes)

        val platformNotification = PlatformNotification(id, dateTime, notifTypes, notifGroupId)
        return platformNotification
    }

    override suspend fun updatePlatformNotification(platformNotification: PlatformNotification) {
        val dbPlatformNotification = platformNotification.toDbPlatformNotification()
        dbPlatformNotificationDao.updatePlatformNotification(dbPlatformNotification)

        val id = platformNotification.id
        androidAlarmScheduler.deleteAlarm(platformNotification.id)
        androidAlarmScheduler.addAlarm(
            id,
            platformNotification.dateTime,
            platformNotification.notifTypes
        ) // TODO (change add alarm to update alarm with some AlarmManager flags)
    }

    override suspend fun deletePlatformNotification(id: PlatformNotificationId) {
        dbPlatformNotificationDao.deletePlatformNotification(id.id)
        androidAlarmScheduler.deleteAlarm(id)
    }

    override suspend fun deleteAllPlatformNotificationsWithNotifGroup(notifGroupId: NotifGroupId) {
        val dbPlatformNotifications =
            dbPlatformNotificationDao.getAllPlatformNotificationsWithNotifGroupId(notifGroupId.id)
                .first()
        for (dbPlatformNotification in dbPlatformNotifications) {
            val id = PlatformNotificationId(dbPlatformNotification.id)
            androidAlarmScheduler.deleteAlarm(id)
        }

        dbPlatformNotificationDao.deleteAllPlatformNotificationWithNotifGroupId(notifGroupId.id)
    }

    override fun getPlatformNotification(id: PlatformNotificationId): Flow<PlatformNotification?> {
        return dbPlatformNotificationDao.getPlatformNotification(id.id).map {
            it?.toPlatformNotification()
        }
    }

    override fun getAllPlatformNotification(): Flow<List<PlatformNotification>> {
        return dbPlatformNotificationDao.getAllPlatformNotifications().map { list ->
            list.map { it.toPlatformNotification() }
        }
    }

    override fun getPlatformNotificationByTime(localDateTime: LocalDateTime): Flow<PlatformNotification?> {
        return dbPlatformNotificationDao.getPlatformNotificationByTime(localDateTime).map {
            it?.toPlatformNotification()
        }
    }
}