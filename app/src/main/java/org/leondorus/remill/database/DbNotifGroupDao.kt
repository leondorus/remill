package org.leondorus.remill.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface DbNotifGroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // TODO(make this overwrite)
    suspend fun insertNotifGroup(notifGroup: DbNotifGroup): Long
    @Update
    suspend fun updateNotifGroup(notifGroup: DbNotifGroup)
    @Query("DELETE FROM DbNotifGroup WHERE id = :id")
    suspend fun deleteNotifGroup(id: Int)

    @Query(
        "SELECT * FROM DbNotifGroup AS ng JOIN DbNotifGroupTime AS ngt ON ng.id = ngt.notifGroupId WHERE ng.id = :id"
    )
    fun getNotifGroupWithTimes(id: Int): Flow<Map<DbNotifGroup, List<DbNotifGroupTime>>>
    @Query(
        "SELECT * FROM DbNotifGroup AS ng JOIN DbNotifGroupTime AS ngt ON ng.id = ngt.notifGroupId"
    )
    fun getAllNotifGroupsWithTimes(): Flow<Map<DbNotifGroup, List<DbNotifGroupTime>>>
    @Query(
        "SELECT * FROM DbNotifGroup AS ng JOIN DbDrug AS d ON ng.id = d.notifGroupId WHERE ng.id = :id"
    )
    fun getNotifGroupWithDrugs(id: Int): Flow<Map<DbNotifGroup, List<DbDrug>>>
    @Query(
        "SELECT * FROM DbNotifGroup AS ng JOIN DbDrug AS d ON ng.id = d.notifGroupId"
    )
    fun getAllNotifGroupsWithDrugs(): Flow<Map<DbNotifGroup, List<DbDrug>>>

    @Insert
    suspend fun insertNotifGroupTimes(notifGroupTimes: List<DbNotifGroupTime>)
    @Delete
    suspend fun deleteNotifGroupTimes(notifGroupTimes: List<DbNotifGroupTime>)
}