package com.kakapo.oakane.presentation.viewModel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.oakane.data.repository.base.BackupRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val backupRepository: BackupRepository
): ViewModel() {

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<SettingsEffect>()

    fun handleEvent(event: SettingsEvent) {
        when(event) {
            SettingsEvent.NavigateBack -> emit(SettingsEffect.NavigateBack)
            SettingsEvent.GenerateBackupFile -> createBackupFile()
            SettingsEvent.RestoreBackupFile -> emit(SettingsEffect.RestoreBackupFile)
            is SettingsEvent.RetrieveBackupFile -> retrieveBackupFile(event.json)
        }
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

    private fun handleError(throwable: Throwable? = null) {
        emit(SettingsEffect.ShowError(throwable?.message ?: "Unknown error"))
    }

    private fun emit(effect: SettingsEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}