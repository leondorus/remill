package org.leondorus.remill.domain

import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.model.NotifType
import org.leondorus.remill.domain.model.NotifTypes
import java.time.LocalDateTime

interface NotifCreator {
    suspend fun addNotification(id: DrugId, localDateTime: LocalDateTime, notifTypes: NotifTypes)
    suspend fun deleteNotification(id: DrugId, localDateTime: LocalDateTime, notifTypes: NotifTypes)
}