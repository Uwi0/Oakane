package com.kakapo.preference.model

data class ReminderPrefs(
    val reminders: List<String>,
    val isReminderEnabled: Boolean,
    val selectedHour: Int,
    val selectedMinute: Int
)
