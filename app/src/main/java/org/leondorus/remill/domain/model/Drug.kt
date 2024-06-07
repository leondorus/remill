package org.leondorus.remill.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class Drug(
    val id: DrugId,
    val name: String,
    val notifGroupId: NotifGroupId?
)

@Serializable
@Parcelize
data class DrugId(val id: Int): Parcelable