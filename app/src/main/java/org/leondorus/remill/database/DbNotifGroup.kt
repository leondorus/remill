package org.leondorus.remill.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.leondorus.remill.domain.model.NotifGroupId
import java.time.LocalDateTime

@Entity
data class DbNotifGroup(
    @PrimaryKey(autoGenerate = true)
    val notifGroupId: Int = 0,
    val name: String = "",
//    @ColumnInfo(name = "use_patterns") val usePatterns: List<UsePattern>
)

@Entity(
    foreignKeys = [ForeignKey(
        entity = DbNotifGroup::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("notifGroupId")
    )]
)
data class DbUsePattern(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @Embedded val notifPatternTypes: DbNotifPatternTypes,
    val notifGroupId: Int,
)

@Entity(
    tableName = "rep_episode", foreignKeys = [ForeignKey(
        entity = DbUsePattern::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("usePatternId")
    )]
)
data class DbRepEpisode(
    @PrimaryKey val id: Int = 0,
    val usePatternId: Int,
    val frequency: Long,
     val numberOfTimes: Long,
)

@Entity(
    tableName = "repeated", primaryKeys = ["repEpisodeId", "ldt"],
    foreignKeys = [
        ForeignKey(
            entity = DbRepEpisode::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("repEpisodeId")
        )
    ]
)
data class DbRepeatedEntry(
    val repEpisodeId: Int,
    val ldt: LocalDateTime,
)

data class DbNotifPatternTypes(
    val push: Boolean,
    val flashlight: Boolean,
    val blinkingMonitor: Boolean,
    val audio: Boolean,
)
