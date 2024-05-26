package org.leondorus.remill.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DrugDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDrugs(vararg dbDrugs: DbDrug): Array<Long>
    @Update
    suspend fun updateDrugs(vararg dbDrugs: DbDrug)
    @Delete
    suspend fun deleteDrugs(vararg dbDrugs: DbDrug)
    @Query("SELECT * FROM DbDrug WHERE drugId = :id")
    fun getDrug(id: Int): Flow<DbDrug>
    @Query("SELECT * FROM DbDrug WHERE drugId = :id")
    fun getDrugWithNotifGroups(id: Int): Flow<DbDrugWithNotifGroups>
    @Query("SELECT * FROM DbDrug")
    fun getAllDrugs(): Flow<List<DbDrug>>
    @Query("SELECT drugId FROM DbDrug")
    fun getAllDrugIds(): Flow<List<Int>>
    @Query("SELECT * FROM DbDrug")
    fun getAllDrugsWithNotifGroups(): Flow<List<DbDrugWithNotifGroups>>
}