package org.leondorus.remill.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.leondorus.remill.domain.model.Drug

@Entity
data class DbDrug(
    @PrimaryKey(autoGenerate = true)
    val drugId: Int = 0,
    val name: String = "",
    val note: String = ""
)
