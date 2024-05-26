package org.leondorus.remill.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.leondorus.remill.domain.NotifGroupEditRepo
import org.leondorus.remill.domain.NotifGroupGetRepo
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.model.NotifGroup
import org.leondorus.remill.domain.model.NotifGroupId
import org.leondorus.remill.domain.model.RepEpisode
import org.leondorus.remill.domain.model.UsePattern

class OfflineNotifGroupRepo(private var notifGroupDao: NotifGroupDao): NotifGroupGetRepo, NotifGroupEditRepo {
    companion object {
        val INFINITY_MARK: Long = UInt.MAX_VALUE.toLong()
    }
    override fun getNotifGroup(id: NotifGroupId): Flow<NotifGroup>
    {
        TODO()
//        notifGroupDao.getNotifGroupWithDrugs(id.id).map { it ->
//        val dbNotifGroup = it.dbNotifGroup
//        val drugIds = it.dbDrugs.map { dbDrug -> DrugId(dbDrug.id) }
//        val usePatterns = notifGroupDao.getUsePatternsForNotifGroup(dbNotifGroup.id).
//            map { dbUsePatterns ->
//                dbUsePatterns.map { dbUsePattern ->
//                    val episodes = notifGroupDao.getRepEpisodesForUsePattern(dbUsePattern.id).map { dbEpisode ->
//                        dbEpisode.map { (episode, entries) ->
//                            val frequncy = episode.frequency
//                            val numberOfTimes = if (episode.numberOfTimes > INFINITY_MARK)
//                        }
//                    }
//                }}
//        NotifGroup(NotifGroupId(dbNotifGroup.id), dbNotifGroup.name,  drugIds, dbNotifGroup.usePatterns)
    }

    override fun getAllNotifGroupIds(): Flow<List<NotifGroupId>> = notifGroupDao.getAllNotifGroupIds().map { intList ->
        intList.map { NotifGroupId(it) }
    }

    override fun getAllNotifGroups(): Flow<List<NotifGroup>> {
        TODO("Not yet implemented")
    }

    override suspend fun addNotifGroup(name: String): NotifGroupId {
        TODO("Not yet implemented")
    }

    override suspend fun setNotifGroupName(id: NotifGroupId, name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun setUsePatterns(id: NotifGroupId, usePatterns: List<UsePattern>) {
        TODO("Not yet implemented")
    }

    override suspend fun addDrugToNotifGroup(notifGroupId: NotifGroupId, drugId: DrugId) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNotifGroup(id: NotifGroupId) {
        TODO("Not yet implemented")
    }
}