package org.leondorus.remill.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class UsePattern(
    val schedule: Schedule,
    val notifTypes: NotifTypes,
)

@Serializable
@Parcelize
data class NotifTypes(
    val push: NotifType.Push,
    val audio: NotifType.Audio,
    val flashlight: NotifType.Flashlight,
    val blinkingScreen: NotifType.BlinkingScreen,
) : Parcelable

@Serializable
sealed class NotifType {
    @Serializable
    @Parcelize
    data class Push(
        val isActive: Boolean,
        val notificationTitle: String,
        val notificationText: String,
        val notificationIcon: Int,
    ) : NotifType(), Parcelable

    @Serializable
    @Parcelize
    data class Audio(
        val isActive: Boolean,
        val audioUri: @Serializable(with = UriSerializer::class) Uri?,
    ) : NotifType(), Parcelable

    @Serializable
    @Parcelize
    data class Flashlight(val isActive: Boolean) : NotifType(), Parcelable

    @Serializable
    @Parcelize
    data class BlinkingScreen(val isActive: Boolean) : NotifType(), Parcelable
}
