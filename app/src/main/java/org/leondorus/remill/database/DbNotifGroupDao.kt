package org.leondorus.remill.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
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
        "SELECT ng.*, d.id AS drugId FROM DbNotifGroup AS ng JOIN DbDrug AS d ON ng.id = d.notifGroupId WHERE ng.id = :id"
    )
    fun getNotifGroupWithDrugs(id: Int): Flow<Map<DbNotifGroup,  List<@MapColumn("drugId") Int>>>
    @Query(
        "SELECT ng.*, d.id AS drugId FROM DbNotifGroup AS ng JOIN DbDrug AS d ON ng.id = d.notifGroupId"
    )
    fun getAllNotifGroupsWithDrugs(): Flow<Map<DbNotifGroup, List<@MapColumn("drugId") Int>>>

    @Insert
    suspend fun insertNotifGroupTimes(notifGroupTimes: List<DbNotifGroupTime>)
    @Delete
    suspend fun deleteNotifGroupTimes(notifGroupTimes: List<DbNotifGroupTime>)
    @Query("DELETE FROM DbNotifGroupTime WHERE notifGroupId = :id")
    suspend fun deleteAllTimesFromNotifGroup(id: Int)
}