package com.kakapo.data.model

import com.kakapo.model.reminder.Reminder
import com.kakapo.model.reminder.toReminderDays
import com.kakapo.preference.model.ReminderPrefs

fun Reminder.toReminderPrefs(): ReminderPrefs {
    return ReminderPrefs(
        reminders = reminders.map { it.title },
        isReminderEnabled = isReminderEnabled,
        selectedHour = selectedHour,
        selectedMinute = selectedMinute
    )
}

fun ReminderPrefs.toReminder(): Reminder {
    return Reminder(
        reminders = reminders.toReminderDays(),
        isReminderEnabled = isReminderEnabled,
        selectedHour = selectedHour,
        selectedMinute = selectedMinute
    )
}