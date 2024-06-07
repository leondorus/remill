package org.leondorus.remill.ui.screens.drug

import android.net.Uri
import android.provider.Settings

sealed class ProposedSound(
    val uri: Uri?,
    val title: String,
) {
    object None : ProposedSound(null, "No sound")
    object Alarm : ProposedSound(Settings.System.DEFAULT_ALARM_ALERT_URI, "Alarm")
    object Notification : ProposedSound(Settings.System.DEFAULT_NOTIFICATION_URI, "Notification")

    object Ringtone : ProposedSound(Settings.System.DEFAULT_RINGTONE_URI, "Ringtone")
}

val proposedSounds: List<ProposedSound> = listOf(
    ProposedSound.None, ProposedSound.Alarm, ProposedSound.Notification, ProposedSound.Ringtone
)