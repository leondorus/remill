package org.leondorus.remill.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = DbNotifGroup::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("notifGroupId")
    )]
)
data class DbDrug(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val notifGroupId: Int?,
)