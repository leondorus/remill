package org.leondorus.remill.domain.model

import java.time.LocalDateTime

data class PlatformNotification(
    val id: PlatformNotificationId,
    val dateTime: LocalDateTime,
    val notifTypes: NotifTypes
)


data class PlatformNotificationId(val id: Int)