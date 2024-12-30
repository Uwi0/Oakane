package com.kakapo.oakane.presentation.viewModel.main

import com.kakapo.oakane.model.system.Theme

data class MainState(
    val theme: Theme = Theme.System
)

sealed class MainEffect {
    data class ShowError(val message: String): MainEffect()
}

sealed class MainEvent {
    data class OnChange(val theme: Theme): MainEvent()
}