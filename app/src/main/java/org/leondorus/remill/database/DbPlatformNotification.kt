package org.leondorus.remill.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.leondorus.remill.domain.model.NotifGroupId
import org.leondorus.remill.domain.model.PlatformNotification
import org.leondorus.remill.domain.model.PlatformNotificationId
import java.time.LocalDateTime

@Entity(
    foreignKeys = [ForeignKey(
        entity = DbNotifGroup::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("notifGroupId")
    )]
)
data class DbPlatformNotification(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(index = true) val dateTime: LocalDateTime,
    @Embedded val notifTypes: DbNotifTypes,
    val notifGroupId: Int,
) {
    fun toPlatformNotification(): PlatformNotification {
        val wrappedId = PlatformNotificationId(id)
        val platformNotification =
            PlatformNotification(wrappedId, dateTime, notifTypes.toNotifTypes(), NotifGroupId( notifGroupId))

        return platformNotification
    }
}

fun PlatformNotification.toDbPlatformNotification(): DbPlatformNotification {
    val dbPlatformNotification =
        DbPlatformNotification(id.id, dateTime, notifTypes.toDbNotifTypes(), notifGroupId.id)
    return dbPlatformNotification
}