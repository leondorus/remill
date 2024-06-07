package org.leondorus.remill.alarms

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import org.leondorus.remill.DRUG_REMINDER_CHANNEL_ID
import org.leondorus.remill.MainActivity
import org.leondorus.remill.domain.model.NotifType
import org.leondorus.remill.domain.model.NotifTypes

private const val TAG = "AlarmReceiver"

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var mp: MediaPlayer
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        Log.i(TAG, "Alarm received with action $action")
        if (action == INTENT_ACTION) {
            val notifTypes: NotifTypes? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(
                        AlarmIntentExtraKeys.NOTIF_TYPES, NotifTypes::class.java
                    )
                } else {
                    intent.getParcelableExtra(AlarmIntentExtraKeys.NOTIF_TYPES)
                }
            val requestCode = intent.getIntExtra(AlarmIntentExtraKeys.PLATFORM_NOTIFICATION_ID, -1)

            if (notifTypes == null || requestCode == -1) {
                Log.e(TAG, "notifTypes=$notifTypes and requestCode=$requestCode, but how?")
                return
            }

            if (notifTypes.push.isActive) {
                setupAndShowNotification(
                    context, notifTypes.push, requestCode
                )
            }
            if (notifTypes.audio.isActive) {
                setupAndPlaySound(context)
            }
        }
    }

    private fun setupAndShowNotification(
        context: Context,
        push: NotifType.Push,
        notificationId: Int,
    ) {
        val pendingIntent = Intent(context, MainActivity::class.java).let {
            PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_IMMUTABLE)
        }
        val builder = NotificationCompat.Builder(context, DRUG_REMINDER_CHANNEL_ID)
            .setSmallIcon(push.notificationIcon).setContentTitle(push.notificationTitle)
            .setContentText(push.notificationText).setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent).setAutoCancel(false)

        with(NotificationManagerCompat.from(context)) {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(notificationId, builder.build())
            }
        }
    }

    private fun setupAndPlaySound(context: Context) {
        mp = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI)
        mp.isLooping = false
        mp.start()
    }
}