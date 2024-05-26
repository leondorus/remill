package org.leondorus.remill.domain.model

data class NotifGroup(
    val id: NotifGroupId,
    val name: String,
    val drugs: List<DrugId>,
    val usePatterns: List<UsePattern>
)

@JvmInline
value class NotifGroupId(val id: Int)

