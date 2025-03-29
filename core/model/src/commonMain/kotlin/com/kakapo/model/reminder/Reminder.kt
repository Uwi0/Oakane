package com.kakapo.model.reminder

data class Reminder(
    val reminders: List<ReminderDay>,
    val isReminderEnabled: Boolean,
    val selectedHour: Int,
    val selectedMinute: Int
)
