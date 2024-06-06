package org.leondorus.remill.alarms

internal const val INTENT_ACTION = "exec_alarm"

internal class AlarmIntentExtraKeys {
    companion object {
        const val NOTIF_TYPES = "notif_types"
        const val PLATFORM_NOTIFICATION_ID = "platform_notification_id"
    }
}