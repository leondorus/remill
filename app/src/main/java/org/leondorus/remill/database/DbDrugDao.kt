package org.leondorus.remill.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface DbDrugDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // TODO(make this overwrite)
    suspend fun insertDrug(dbDrug: DbDrug): Long
    @Update
    suspend fun updateDrug(dbDrug: DbDrug)
    @Query("DELETE FROM DbDrug WHERE id = :id")
    suspend fun deleteDrug(id: Int)

    @Query("SELECT * FROM DbDrug WHERE id = :id")
    fun getDrug(id: Int): Flow<DbDrug?>
    @Query("SELECT * FROM DbDrug")
    fun getAllDrugs(): Flow<List<DbDrug>>
}