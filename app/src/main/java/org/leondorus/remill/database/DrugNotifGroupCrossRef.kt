package org.leondorus.remill.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    primaryKeys = ["drugId", "notifGroupId"],
    foreignKeys = [
        ForeignKey(
            entity = DbDrug::class,
            parentColumns = arrayOf("drugId"),
            childColumns = arrayOf("drugId")
        ),
        ForeignKey(
            entity = DbNotifGroup::class,
            parentColumns = arrayOf("notifGroupId"),
            childColumns = arrayOf("notifGroupId")
        )
    ]
)
data class DrugNotifGroupCrossRef(
    val drugId: Int,
    val notifGroupId: Int,
)
data class DbDrugWithNotifGroups(
    @Embedded val dbDrug: DbDrug,
    @Relation(
        parentColumn = "drugId",
        entityColumn = "notifGroupId",
        associateBy = Junction(DrugNotifGroupCrossRef::class)
    )
    val dbNotifGroups: List<DbNotifGroup>
)

data class DbNotifGroupWithDrugs(
    @Embedded val dbNotifGroup: DbNotifGroup,
    @Relation(
        parentColumn = "notifGroupId",
        entityColumn = "drugId",
        associateBy = Junction(DrugNotifGroupCrossRef::class)
    )
    val dbDrugs: List<DbDrug>
)