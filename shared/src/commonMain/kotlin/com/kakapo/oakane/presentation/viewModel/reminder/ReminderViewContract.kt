package com.kakapo.oakane.presentation.viewModel.reminder

data class ReminderState(
    val selectedHour: Int = 19,
    val selectedMinute: Int = 0,
) {
    val selectedTime: String get() {
        val hour = selectedHour.toString().padStart(2, '0')
        val minute = selectedMinute.toString().padStart(2, '0')
        return "$hour:$minute"
    }
}