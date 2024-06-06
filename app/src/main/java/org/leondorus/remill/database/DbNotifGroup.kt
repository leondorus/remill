package org.leondorus.remill.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.leondorus.remill.domain.model.NotifType
import org.leondorus.remill.domain.model.NotifTypes
import java.time.LocalDateTime

@Entity
data class DbNotifGroup(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @Embedded val notifTypes: DbNotifTypes,
)

@Entity(
    primaryKeys = ["notifGroupId", "dateTime"], foreignKeys = [ForeignKey(
        entity = DbNotifGroup::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("notifGroupId")
    )]
)
data class DbNotifGroupTime(
    val notifGroupId: Int,
    val dateTime: LocalDateTime,
)

data class DbNotifTypes(
    @Embedded(prefix = "push") val push: DbNotifTypePush,
    @Embedded(prefix = "audio") val audio: DbNotifTypeAudio,
    @Embedded(prefix = "flashlight") val flashlight: DbNotifTypeFlashlight,
    @Embedded(prefix = "blinkingScreen") val blinkingScreen: DbNotifTypeBlinkingScreen
) {
    fun toNotifTypes(): NotifTypes {
        val notifTypes = NotifTypes(
            NotifType.Push(
                isActive = push.isActive,
                notificationTitle = push.notificationTitle,
                notificationText = push.notificationText,
                notificationIcon = push.notificationIcon
            ),
            NotifType.Audio(audio.isActive),
            NotifType.Flashlight(flashlight.isActive),
            NotifType.BlinkingScreen(blinkingScreen.isActive),
        )
        return notifTypes
    }
}

fun NotifTypes.toDbNotifTypes(): DbNotifTypes {
    val dbNotifTypes = DbNotifTypes(
        push = DbNotifTypePush(
            isActive = push.isActive,
            notificationTitle = push.notificationTitle,
            notificationText = push.notificationText,
            notificationIcon = push.notificationIcon
        ),
        audio = DbNotifTypeAudio(audio.isActive),
        flashlight = DbNotifTypeFlashlight(flashlight.isActive),
        blinkingScreen = DbNotifTypeBlinkingScreen(blinkingScreen.isActive),
    )
    return dbNotifTypes
}

data class DbNotifTypePush(
    val isActive: Boolean,
    val notificationTitle: String,
    val notificationText: String,
    val notificationIcon: Int
)

data class DbNotifTypeAudio(
    val isActive: Boolean,
)

data class DbNotifTypeFlashlight(
    val isActive: Boolean,
)

data class DbNotifTypeBlinkingScreen(
    val isActive: Boolean,
)