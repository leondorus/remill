package org.leondorus.remill.domain.model

data class UsePattern(
    val schedule: Schedule,
    val notifTypes: NotifTypes,
)

data class NotifTypes(
    val push: NotifType.Push = NotifType.Push(),
    val audio: NotifType.Audio = NotifType.Audio(),
    val flashlight: NotifType.Flashlight = NotifType.Flashlight(),
    val blinkingScreen: NotifType.BlinkingScreen = NotifType.BlinkingScreen()
)

sealed class NotifType(val isActive: Boolean) {
    class Push(isActive: Boolean = false) : NotifType(isActive)
    class Audio(isActive: Boolean = false) : NotifType(isActive)
    class Flashlight(isActive: Boolean = false) : NotifType(isActive)
    class BlinkingScreen(isActive: Boolean = false) : NotifType(isActive)
}
