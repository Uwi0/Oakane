package com.kakapo.oakane.presentation.viewModel.settings

import com.kakapo.model.system.Theme
import com.kakapo.model.system.asTheme
import kotlin.native.ObjCName

@ObjCName("SettingsStateKt")
data class SettingsState(
    val themeMode: Theme = Theme.System,
    val isDialogShown: Boolean = false,
) {
    fun update(theme: Int) = copy(
        themeMode = theme.asTheme()
    )
}

sealed class SettingsEffect {
    data object NavigateBack: SettingsEffect()
    data class ShowError(val message: String): SettingsEffect()
    data class GenerateBackupFile(val json: String): SettingsEffect()
    data object RestoreBackupFile: SettingsEffect()
    data class Confirm(val theme: Theme): SettingsEffect()
}

sealed class SettingsEvent {
    data object NavigateBack: SettingsEvent()
    data object GenerateBackupFile: SettingsEvent()
    data object RestoreBackupFile: SettingsEvent()
    data class RetrieveBackupFile(val json: String): SettingsEvent()
    data class OnDialog(val shown: Boolean): SettingsEvent()
    data class OnSelected(val theme: Int): SettingsEvent()
    data object OnConfirmTheme: SettingsEvent()
}