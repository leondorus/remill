package org.leondorus.remill.domain.model


data class UsePattern(
    val genNextDates: Schedule,
    val notifTypes: NotifTypes,
)

data class NotifTypes(
    val push: NotifType.Push,
    val audio: NotifType.Audio,
    val flashlight: NotifType.Flashlight,
    val blinkingScreen: NotifType.BlinkingScreen
)

sealed class NotifType(val isActive: Boolean) {
    class Push(isActive: Boolean) : NotifType(isActive)
    class Audio(isActive: Boolean) : NotifType(isActive)
    class Flashlight(isActive: Boolean) : NotifType(isActive)
    class BlinkingScreen(isActive: Boolean) : NotifType(isActive)
}
