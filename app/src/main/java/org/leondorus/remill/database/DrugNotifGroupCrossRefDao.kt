package org.leondorus.remill.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface DrugNotifGroupCrossRefDao {
    @Insert
    suspend fun insertDrugNotifGroupCrossRef(drugNotifGroupCrossRef: DrugNotifGroupCrossRef)
    @Delete
    suspend fun deleteDrugNotifGroupCrossRef(drugNotifGroupCrossRef: DrugNotifGroupCrossRef)
}