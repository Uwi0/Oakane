package com.kakapo.oakane.presentation.viewModel.reminder

import com.kakapo.model.reminder.ReminderDay

data class ReminderState(
    val enabledReminder: Boolean = false,
    val selectedHour: Int = 19,
    val selectedMinute: Int = 0,
    val selectedDays: List<ReminderDay> = ReminderDay.entries,
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

    fun updateSelected(day: ReminderDay): ReminderState {
        val updatedDays = if (selectedDays.contains(day)) {
            selectedDays - day
        } else {
            selectedDays + day
        }
        return copy(selectedDays = updatedDays)
    }
}

sealed class ReminderEvent {
    data class ToggleReminder(val enabled: Boolean): ReminderEvent()
    data class Dialog(val shown: Boolean = false): ReminderEvent()
    data class TimeSelected(val hour: Int, val minute: Int): ReminderEvent()
    data class DaySelected(val day: ReminderDay): ReminderEvent()
}