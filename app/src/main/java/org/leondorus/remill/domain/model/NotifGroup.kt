package org.leondorus.remill.domain.model

data class NotifGroup(
    val id: NotifGroupId,
    val name: String,
    val usePattern: UsePattern,
    val drugs: List<DrugId>,
)

data class NotifGroupId(val id: Int)