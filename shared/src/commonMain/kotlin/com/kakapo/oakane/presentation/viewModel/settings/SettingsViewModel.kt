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
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState
    private val _uiState = MutableStateFlow(SettingsState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<SettingsEffect>()

    fun initData(showDrawer: Boolean) {
        _uiState.update { it.copy(showDrawer = showDrawer) }
        loadIsDarkMode()
        loadCurrency()
        loadIsBudgetRecurring()
        loadIsCategoryLimitRecurring()
    }

    fun handleEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.NavigateBack -> emit(SettingsEffect.NavigateBack)
            SettingsEvent.GenerateBackupFile -> createBackupFile()
            SettingsEvent.RestoreBackupFile -> emit(SettingsEffect.RestoreBackupFile)
            SettingsEvent.OnConfirmTheme -> confirmTheme()
            is SettingsEvent.RetrieveBackupFile -> retrieveBackupFile(event.json)
            is SettingsEvent.OnDialog -> _uiState.update { it.copy(isDialogShown = event.shown) }
            is SettingsEvent.OnSelected -> _uiState.update { it.update(event.theme) }
            is SettingsEvent.OnSheet -> _uiState.update { it.copy(isSheetShown = event.shown) }
            is SettingsEvent.ChangeCurrency -> changeCurrency(event.currency)
            is SettingsEvent.ToggleRecurringBudget -> setMonthlyBudget(event.isRecurring)
            is SettingsEvent.ToggleRecurringCategoryLimit -> setCategoryLimit(event.isRecurring)
            is SettingsEvent.ToggleAlarm -> _uiState.update { it.copy(alarmEnabled = event.enabled) }
            SettingsEvent.OpenDrawer -> emit(SettingsEffect.OpenDrawer)
            is SettingsEvent.UpdateDay -> _uiState.update { it.update(event.day) }
        }
    }

    private fun loadIsDarkMode() = viewModelScope.launch {
        val onSuccess: (Theme) -> Unit = { themeMode ->
            _uiState.value = _uiState.value.copy(theme = themeMode)
        }

        systemRepository.loadSavedTheme().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun createBackupFile() = viewModelScope.launch {
        backupRepository.createBackup().fold(
            onSuccess = { emit(SettingsEffect.GenerateBackupFile(it)) },
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
        val theme = uiState.value.theme
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

    private fun loadIsBudgetRecurring() = viewModelScope.launch {
        val onSuccess: (Boolean) -> Unit = { isRecurring ->
            _uiState.value = _uiState.value.copy(isRecurringBudget = isRecurring)
        }
        systemRepository.isMonthlyBudgetRecurring().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun loadIsCategoryLimitRecurring() = viewModelScope.launch {
        val onSuccess: (Boolean) -> Unit = { isRecurring ->
            _uiState.value = _uiState.value.copy(isRecurringCategoryLimit = isRecurring)
        }
        systemRepository.isCategoryLimitRecurring().fold(
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

    private fun setMonthlyBudget(isRecurring: Boolean) = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            _uiState.update { it.copy(isRecurringBudget = isRecurring) }
            if (!isRecurring) setCategoryLimit(isRecurring = false)
        }
        systemRepository.setMonthlyBudget(isRecurring).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun setCategoryLimit(isRecurring: Boolean) = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            _uiState.update { it.copy(isRecurringCategoryLimit = isRecurring) }
        }
        systemRepository.saveCategoryLimit(isRecurring).fold(
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