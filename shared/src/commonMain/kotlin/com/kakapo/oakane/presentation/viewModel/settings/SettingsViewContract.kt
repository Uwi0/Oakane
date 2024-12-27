package com.kakapo.oakane.presentation.viewModel.settings

sealed class SettingsEffect {
    data object NavigateBack: SettingsEffect()
    data class GenerateBackupFile(val json: String): SettingsEffect()
}

sealed class SettingsEvent {
    data object NavigateBack: SettingsEvent()
    data object GenerateBackupFile: SettingsEvent()
}