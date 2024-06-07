package org.leondorus.remill

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

const val DRUG_REMINDER_CHANNEL_ID = "drug_reminders"

class RemillApplication : Application() {
    lateinit var container: RemillContainer

    override fun onCreate() {
        super.onCreate()
        container = AndroidRemillContainer(this)
        setupNotificationChannels()
//        cancelAllAlarms()
    }

    private fun setupNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.drug_reminder_channel_name)
            val description = getString(R.string.drug_reminder_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(DRUG_REMINDER_CHANNEL_ID, name, importance).apply {
                this.description = description
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun cancelAllAlarms() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancelAll()
        }
    }

}
