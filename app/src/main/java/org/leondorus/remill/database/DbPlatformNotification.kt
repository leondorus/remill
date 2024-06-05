package org.leondorus.remill.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.leondorus.remill.domain.model.PlatformNotification
import org.leondorus.remill.domain.model.PlatformNotificationId
import java.time.LocalDateTime

@Entity
data class DbPlatformNotification(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateTime: LocalDateTime,
    @Embedded val notifTypes: DbNotifTypes
) {
    fun toPlatformNotification(): PlatformNotification {
        val wrappedId = PlatformNotificationId(id)
        val platformNotification = PlatformNotification(wrappedId, dateTime, notifTypes.toNotifTypes())

        return platformNotification
    }
}

fun PlatformNotification.toDbPlatformNotification(): DbPlatformNotification {
    val dbPlatformNotification = DbPlatformNotification(id.id, dateTime, notifTypes.toDbNotifTypes())
    return dbPlatformNotification
}