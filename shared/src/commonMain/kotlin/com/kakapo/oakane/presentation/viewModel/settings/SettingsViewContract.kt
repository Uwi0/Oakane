package com.kakapo.oakane.presentation.viewModel.settings

import com.kakapo.model.Currency
import com.kakapo.model.system.Theme
import com.kakapo.model.system.asTheme
import com.kakapo.oakane.presentation.model.ReminderDay
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
    val alarmValue: String = "19:00",
    val showDrawer: Boolean = false,
    val selectedDays: List<ReminderDay> = ReminderDay.entries,
    val dialogContent: SettingsDialogContent = SettingsDialogContent.Theme
) {
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
}

enum class SettingsDialogContent {
    Theme,
    Reminder
}