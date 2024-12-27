package com.kakapo.oakane.presentation.viewModel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SettingsViewModel: ViewModel() {

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<SettingsEffect>()

    fun handleEvent(event: SettingsEvent) {
        when(event) {
            SettingsEvent.NavigateBack -> emit(SettingsEffect.NavigateBack)
        }
    }

    private fun emit(effect: SettingsEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}