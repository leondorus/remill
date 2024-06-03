package org.leondorus.remill.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Drug(
    val id: DrugId,
    val name: String,
    val notifGroupId: NotifGroupId?
)

@Parcelize
data class DrugId(val id: Int): Parcelable