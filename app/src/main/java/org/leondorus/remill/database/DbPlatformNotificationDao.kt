package org.leondorus.remill.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DbPlatformNotificationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // TODO(make this overwrite)
    suspend fun insertPlatformNotification(dbPlatformNotification: DbPlatformNotification): Long
    @Update
    suspend fun updatePlatformNotification(dbPlatformNotification: DbPlatformNotification)
    @Query("DELETE FROM DbPlatformNotification WHERE id = :id")
    suspend fun deletePlatformNotification(id: Int)

    @Query("SELECT * FROM DbPlatformNotification WHERE id = :id")
    fun getPlatformNotification(id: Int): Flow<DbPlatformNotification?>
    @Query("SELECT * FROM DbPlatformNotification")
    fun getAllPlatformNotifications(): Flow<List<DbPlatformNotification>>
}