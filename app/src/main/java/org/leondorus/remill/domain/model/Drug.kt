package org.leondorus.remill.domain.model

data class Drug(
    val id: DrugId,
    val name: String,
    val note: String,
    val notifGroups: List<NotifGroupId>,
    //val photos: List<Photo>
    //val properties: contradictions, useBefore, useWith, useAfter, useMethod

)

@JvmInline
value class DrugId(val id: Int)

