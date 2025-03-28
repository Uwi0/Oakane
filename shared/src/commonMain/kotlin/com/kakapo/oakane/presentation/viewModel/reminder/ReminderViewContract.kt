package com.kakapo.oakane.presentation.viewModel.reminder

import com.kakapo.model.reminder.ReminderDay

data class ReminderState(
    val selectedHour: Int = 19,
    val selectedMinute: Int = 0,
    val selectedDays: List<ReminderDay> = emptyList(),
    val showDialog: Boolean = false
) {

    val selectedTime: String get() {
        val hour = selectedHour.toString().padStart(2, '0')
        val minute = selectedMinute.toString().padStart(2, '0')
        return "$hour:$minute"
    }

    fun updateSelectedTime(event: ReminderEvent.TimeSelected): ReminderState {
        return copy(selectedHour = event.hour, selectedMinute = event.minute, showDialog = false)
    }
}

sealed class ReminderEvent {
    data class Dialog(val shown: Boolean = false): ReminderEvent()
    data class TimeSelected(val hour: Int, val minute: Int): ReminderEvent()
}