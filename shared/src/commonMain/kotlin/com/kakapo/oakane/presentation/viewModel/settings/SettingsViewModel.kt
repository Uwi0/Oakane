package com.kakapo.oakane.presentation.viewModel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.oakane.data.repository.base.BackupRepository
import com.kakapo.oakane.data.repository.base.SystemRepository
import com.kakapo.oakane.model.system.Theme
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val backupRepository: BackupRepository,
    private val systemRepository: SystemRepository
): ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState
    private val _uiState = MutableStateFlow(SettingsState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<SettingsEffect>()

    fun initData() {
        loadIsDarkMode()
    }

    fun handleEvent(event: SettingsEvent) {
        when(event) {
            SettingsEvent.NavigateBack -> emit(SettingsEffect.NavigateBack)
            SettingsEvent.GenerateBackupFile -> createBackupFile()
            SettingsEvent.RestoreBackupFile -> emit(SettingsEffect.RestoreBackupFile)
            is SettingsEvent.RetrieveBackupFile -> retrieveBackupFile(event.json)
            is SettingsEvent.OnDialog -> _uiState.update { it.copy(isDialogShown = event.shown) }
            is SettingsEvent.OnSelected -> _uiState.update { it.update(event.theme) }
            SettingsEvent.OnConfirmTheme -> confirmTheme()
        }
    }

    private fun loadIsDarkMode() = viewModelScope.launch {
        val onSuccess: (Theme) -> Unit = { themeMode ->
            _uiState.value = _uiState.value.copy(themeMode = themeMode)
        }

        systemRepository.loadSavedTheme().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun createBackupFile() = viewModelScope.launch {
        backupRepository.createBackup().fold(
            onSuccess = { emit(SettingsEffect.GenerateBackupFile(it))},
            onFailure = ::handleError
        )
    }

    private fun retrieveBackupFile(json: String) = viewModelScope.launch {
        backupRepository.restoreBackup(json).fold(
            onSuccess = { emit(SettingsEffect.NavigateBack) },
            onFailure = ::handleError
        )
    }

    private fun confirmTheme() = viewModelScope.launch {
        val theme = uiState.value.themeMode
        val onSuccess: (Unit) -> Unit = {
            _uiState.update { it.copy(isDialogShown = false) }
            emit(SettingsEffect.Confirm(theme))
        }

        systemRepository.saveTheme(theme).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable? = null) {
        emit(SettingsEffect.ShowError(throwable?.message ?: "Unknown error"))
    }

    private fun emit(effect: SettingsEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}