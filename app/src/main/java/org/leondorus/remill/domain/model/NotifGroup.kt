package org.leondorus.remill.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NotifGroup(
    val id: NotifGroupId,
    val name: String,
    val usePattern: UsePattern,
    val drugs: List<DrugId>,
)

@Serializable
data class NotifGroupId(val id: Int)