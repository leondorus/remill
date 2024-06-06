package org.leondorus.remill.alarms

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import org.leondorus.remill.domain.model.NotifTypes
import org.leondorus.remill.domain.model.PlatformNotificationId
import java.time.LocalDateTime
import java.time.ZoneId

class AndroidAlarmSchedulerImpl(private val context: Context): AndroidAlarmScheduler {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    @SuppressLint("MissingPermission")
    override fun addAlarm(id: PlatformNotificationId, ldt: LocalDateTime, notifTypes: NotifTypes) {
        val alarmIntent = generatePendingIntent(id, notifTypes)
        val alarmTime = ldt.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000L
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, alarmIntent)
    }

    override fun deleteAlarm(id: PlatformNotificationId) {
        val alarmIntent = generatePendingIntent(id)
        alarmManager.cancel(alarmIntent)
    }

    private fun generatePendingIntent(id: PlatformNotificationId, notifTypes: NotifTypes? = null): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = INTENT_ACTION
            if (notifTypes != null) {
                putExtra(AlarmIntentExtraKeys.PLATFORM_NOTIFICATION_ID, id.id)
                putExtra(AlarmIntentExtraKeys.NOTIF_TYPES, notifTypes)
            }

        }
        val pendingIntent = PendingIntent.getBroadcast(context, id.id, intent, FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)

        return pendingIntent
    }
}