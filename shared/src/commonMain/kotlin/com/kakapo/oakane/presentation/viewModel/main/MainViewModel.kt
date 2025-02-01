package com.kakapo.oakane.presentation.viewModel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.domain.usecase.base.ImportRecurringBudgetUseCase
import com.kakapo.model.system.Theme
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val systemRepository: SystemRepository,
    private val importRecurringBudgetUseCase: ImportRecurringBudgetUseCase
): ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(MainState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect
    private val _uiEffect = MutableSharedFlow<MainEffect>()

    fun initData() {
        loadTheme()
        importRecurringBudget()
    }

    fun handleEvent(event: MainEvent) {
        when(event) {
            is MainEvent.OnChange -> _uiState.update { it.copy(theme = event.theme) }
        }
    }

    private fun loadTheme() = viewModelScope.launch {
        val onSuccess: (Theme) -> Unit = { theme ->
            _uiState.update { it.copy(theme = theme) }
        }
        systemRepository.loadSavedTheme().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun importRecurringBudget() = viewModelScope.launch {
        importRecurringBudgetUseCase.execute().fold(
            onSuccess = { Logger.d("ImportRecurringBudgetUseCaseImpl called") },
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        emit(MainEffect.ShowError(throwable?.message ?: "Unknown error"))
    }

    private fun emit(effect: MainEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}