package org.leondorus.remill.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.RepEpisode

@Dao
interface NotifGroupDao {
    @Insert
    suspend fun insertNotifGroups(vararg notifGroups: DbNotifGroup)

    @Update
    suspend fun updateNotifGroups(vararg notifGroups: DbNotifGroup)

    @Delete
    suspend fun deleteNotifGroups(vararg notifGroups: DbNotifGroup)

    @Query("SELECT * FROM notifgroup WHERE id = :id")
    fun getNotifGroup(id: Int): Flow<DbNotifGroup>

    @Query("SELECT * FROM notifgroup")
    fun getAllNotifGroups(): Flow<List<DbNotifGroup>>

    @Query("SELECT id FROM notifgroup")
    fun getAllNotifGroupIds(): Flow<List<Int>>

    @Transaction
    @Query("SELECT * FROM notifgroup WHERE id = :id")
    fun getNotifGroupWithDrugs(id: Int): Flow<DbNotifGroupWithDrugs>

    @Transaction
    @Query("SELECT * FROM notifgroup")
    fun getAllNotifGroupsWithDrugs(): Flow<List<DbNotifGroupWithDrugs>>

    @Query("SELECT * FROM use_pattern WHERE notifgroup_id = :id")
    fun getUsePatternsForNotifGroup(id: Int): Flow<List<DbUsePattern>>

    @Query(
        "SELECT * FROM rep_episode " +
        "JOIN repeated ON rep_episode.id = repeated.rep_episode_id " +
        "WHERE rep_episode.use_pattern_id = :id"
    )
    fun getRepEpisodesForUsePattern(id: Int): Flow<Map<DbRepEpisode, List<DbRepeatedEntry>>>
}