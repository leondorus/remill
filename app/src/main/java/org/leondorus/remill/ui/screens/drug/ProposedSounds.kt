package org.leondorus.remill.ui.screens.drug

import android.net.Uri
import android.provider.Settings

sealed class ProposedSound(
    val uri: Uri?,
    val title: String,
) {
    data object None : ProposedSound(null, "No sound")
    data object Alarm : ProposedSound(Settings.System.DEFAULT_ALARM_ALERT_URI, "Alarm")
    data object Notification : ProposedSound(Settings.System.DEFAULT_NOTIFICATION_URI, "Notification")

    data object Ringtone : ProposedSound(Settings.System.DEFAULT_RINGTONE_URI, "Ringtone")
}

val proposedSounds: List<ProposedSound> = listOf(
    ProposedSound.None, ProposedSound.Alarm, ProposedSound.Notification, ProposedSound.Ringtone
)