package com.kakapo.oakane.presentation.viewModel.settings

import com.kakapo.model.Currency
import com.kakapo.model.reminder.Reminder
import com.kakapo.model.system.Theme
import com.kakapo.model.system.asTheme
import com.kakapo.model.reminder.ReminderDay
import kotlin.native.ObjCName

@ObjCName("SettingsStateKt")
data class SettingsState(
    val theme: Theme = Theme.System,
    val dialogShown: Boolean = false,
    val currency: Currency = Currency.USD,
    val isSheetShown: Boolean = false,
    val isRecurringBudget: Boolean = false,
    val isRecurringCategoryLimit: Boolean = false,
    val alarmEnabled: Boolean = false,
    val showDrawer: Boolean = false,
    val selectedDays: List<ReminderDay> = ReminderDay.entries,
    val dialogContent: SettingsDialogContent = SettingsDialogContent.Theme,
    val selectedHour: Int = 19,
    val selectedMinute: Int = 0
) {
    val alarmValue: String get() {
        val hour = selectedHour.toString().padStart(2, '0')
        val minute = selectedMinute.toString().padStart(2, '0')
        return "$hour:$minute"
    }

    fun update(theme: Int) = copy(
        theme = theme.asTheme()
    )

    fun update(day: ReminderDay) = copy(
        selectedDays = if (selectedDays.contains(day)) {
            selectedDays - day
        } else {
            selectedDays + day
        }
    )

    fun updateHourAndMinutesWith(event: SettingsEvent.UpdateHourAndMinute) = copy(
        selectedHour = event.hour,
        selectedMinute = event.minute,
        dialogShown = false
    )

    fun updateReminder(reminder: Reminder) = copy(
        selectedHour = reminder.selectedHour,
        selectedMinute = reminder.selectedMinute,
        alarmEnabled = reminder.isReminderEnabled,
        selectedDays = reminder.reminders
    )
}

sealed class SettingsEffect {
    data object NavigateBack: SettingsEffect()
    data class ShowError(val message: String): SettingsEffect()
    data class GenerateBackupFile(val json: String): SettingsEffect()
    data object RestoreBackupFile: SettingsEffect()
    data class Confirm(val theme: Theme): SettingsEffect()
    data object SuccessChangeCurrency: SettingsEffect()
    data object OpenDrawer: SettingsEffect()
}

sealed class SettingsEvent {
    data object NavigateBack: SettingsEvent()
    data object GenerateBackupFile: SettingsEvent()
    data object RestoreBackupFile: SettingsEvent()
    data class RetrieveBackupFile(val json: String): SettingsEvent()
    data class ShowDialog(val content: SettingsDialogContent, val shown: Boolean): SettingsEvent()
    data class OnSheet(val shown: Boolean): SettingsEvent()
    data class Selected(val theme: Int): SettingsEvent()
    data object OnConfirmTheme: SettingsEvent()
    data class ChangeCurrency(val currency: Currency): SettingsEvent()
    data class ToggleRecurringBudget(val isRecurring: Boolean): SettingsEvent()
    data class ToggleRecurringCategoryLimit(val isRecurring: Boolean): SettingsEvent()
    data object OpenDrawer: SettingsEvent()
    data class ToggleAlarm(val enabled: Boolean): SettingsEvent()
    data class UpdateDay(val day: ReminderDay): SettingsEvent()
    data class UpdateHourAndMinute(val hour: Int, val minute: Int): SettingsEvent()
}

enum class SettingsDialogContent {
    Theme,
    Reminder
}