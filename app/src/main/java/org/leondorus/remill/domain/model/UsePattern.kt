package org.leondorus.remill.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UsePattern(
    val schedule: Schedule,
    val notifTypes: NotifTypes,
)


@Parcelize
data class NotifTypes(
    val push: NotifType.Push = NotifType.Push(),
    val audio: NotifType.Audio = NotifType.Audio(),
    val flashlight: NotifType.Flashlight = NotifType.Flashlight(),
    val blinkingScreen: NotifType.BlinkingScreen = NotifType.BlinkingScreen()
): Parcelable

sealed class NotifType(open val isActive: Boolean = false) {
    @Parcelize
    class Push(override val isActive: Boolean = false) : NotifType(), Parcelable
    @Parcelize
    class Audio(override val isActive: Boolean = false) : NotifType(), Parcelable
    @Parcelize
    class Flashlight(override val isActive: Boolean = false) : NotifType(), Parcelable
    @Parcelize
    class BlinkingScreen(override val isActive: Boolean = false) : NotifType(), Parcelable
}
