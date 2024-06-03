package org.leondorus.remill.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class DbNotifGroup(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @Embedded val usePattern: DbUsePattern,
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

data class DbUsePattern(
    @Embedded val push: DbNotifTypePush,
    @Embedded val audio: DbNotifTypeAudio,
    @Embedded val flashlight: DbNotifTypeFlashlight,
    @Embedded val blinkingScreen: DbNotifTypeBlinkingScreen
)

data class DbNotifTypePush(
    val isActive: Boolean,
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
