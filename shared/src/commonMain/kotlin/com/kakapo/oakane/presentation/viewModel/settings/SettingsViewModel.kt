package com.kakapo.oakane.presentation.viewModel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.BackupRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.model.Currency
import com.kakapo.model.system.Theme
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("SettingsViewModelKt")
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
        loadCurrency()
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
            is SettingsEvent.OnSheet -> _uiState.update { it.copy(isSheetShown = event.shown) }
            is SettingsEvent.ChangeCurrency -> changeCurrency(event.currency)
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

    private fun loadCurrency() = viewModelScope.launch {
        val onSuccess: (Currency) -> Unit = { currency ->
            _uiState.value = _uiState.value.copy(currency = currency)
        }
        systemRepository.loadSavedCurrency().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun changeCurrency(currency: Currency) = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            _uiState.update { it.copy(currency = currency, isSheetShown = false) }
            emit(SettingsEffect.SuccessChangeCurrency)
        }
        systemRepository.saveCurrency(currency).fold(
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