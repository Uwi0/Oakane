package com.kakapo.oakane.presentation.viewModel.settings

sealed class SettingsEffect {
    data object NavigateBack: SettingsEffect()
}

sealed class SettingsEvent {
    data object NavigateBack: SettingsEvent()
}