package org.leondorus.remill.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

private const val TAG = "AlarmReceiver"
class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action

        Log.i(TAG, "Alarm received with action $action")
        if (action == INTENT_ACTION) {
            TODO("Not yet implemented")
        }
    }
}