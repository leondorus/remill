package org.leondorus.remill.alarms

import org.leondorus.remill.domain.model.NotifTypes
import org.leondorus.remill.domain.model.PlatformNotificationId
import java.time.LocalDateTime

interface AndroidAlarmScheduler {
    fun addAlarm(id: PlatformNotificationId, ldt: LocalDateTime, notifTypes: NotifTypes)
    fun deleteAlarm(id: PlatformNotificationId)
}