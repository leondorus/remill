package org.leondorus.remill.domain.sharing

import kotlinx.serialization.Serializable
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.NotifGroup
import java.time.LocalDateTime

@Serializable
data class FullDrugInfo(
    val drug: Drug,
    val notifGroup: NotifGroup?,
)

typealias HolyTriple = Pair<LocalDateTime, FullDrugInfo>
