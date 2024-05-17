package org.leondorus.remill.domain.model

data class Drug(
    val id: DrugId,
    val name: String,
    val notifGroup: List<NotifGroupId>
)

data class DrugId(val id: Int)