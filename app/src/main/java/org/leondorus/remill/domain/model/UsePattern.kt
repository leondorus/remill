package org.leondorus.remill.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.leondorus.remill.R

data class UsePattern(
    val schedule: Schedule,
    val notifTypes: NotifTypes,
)


//@Parcelize
//data class NotifTypes(
//    val push: NotifType.Push = NotifType.Push(false, "", "//", R.drawable.notification_icon),
//    val audio: NotifType.Audio = NotifType.Audio(false),
//    val flashlight: NotifType.Flashlight = NotifType.Flashlight(false),
//    val blinkingScreen: NotifType.BlinkingScreen = NotifType.BlinkingScreen(false)
//): Parcelable

@Parcelize
data class NotifTypes(
    val push: NotifType.Push,
    val audio: NotifType.Audio,
    val flashlight: NotifType.Flashlight,
    val blinkingScreen: NotifType.BlinkingScreen
): Parcelable

sealed class NotifType(open val isActive: Boolean) {
    @Parcelize
    data class Push(
        override val isActive: Boolean,
        val notificationTitle: String,
        val notificationText: String,
        val notificationIcon: Int
    ) : NotifType(isActive), Parcelable
    @Parcelize
    data class Audio(override val isActive: Boolean) : NotifType(isActive), Parcelable
    @Parcelize
    data class Flashlight(override val isActive: Boolean) : NotifType(isActive), Parcelable
    @Parcelize
    data class BlinkingScreen(override val isActive: Boolean) : NotifType(isActive), Parcelable
}
