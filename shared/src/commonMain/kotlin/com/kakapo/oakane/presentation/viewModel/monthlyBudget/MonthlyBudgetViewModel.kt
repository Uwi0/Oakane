package com.kakapo.oakane.presentation.viewModel.monthlyBudget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MonthlyBudgetViewModel(
    private val repository: MonthlyBudgetRepository
): ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(MonthlyBudgetState())

    val effect get() = _effect.asSharedFlow()
    private val _effect = MutableSharedFlow<MonthlyBudgetEffect>()

    fun initializeData(){
        checkTableIsNotEmpty()
    }

    fun handleEvent(event: MonthlyBudgetEvent) {
        when(event){
            MonthlyBudgetEvent.NavigateBack -> emit(MonthlyBudgetEffect.NavigateBack)
            MonthlyBudgetEvent.Save -> saveBudget()
            is MonthlyBudgetEvent.Changed -> _uiState.update { it.copy(amount = event.amount) }
        }
    }

    private fun checkTableIsNotEmpty() = viewModelScope.launch {
        repository.tableNotEmpty().fold(
            onSuccess = { tableNotEmpty ->
                Logger.d(messageString = "Table is not empty: $tableNotEmpty")
                _uiState.update { it.copy(isEditMode = tableNotEmpty) }
            },
            onFailure = {
                Logger.e(messageString = it.message.toString())
            }
        )
    }

    private fun saveBudget() = viewModelScope.launch {
        val monthlyBudget = uiState.value.asMonthlyBudgetParam()
        repository.save(monthlyBudget).fold(
            onSuccess = {
                emit(MonthlyBudgetEffect.NavigateBack)
            },
            onFailure = {
                Logger.e(messageString = it.message.toString())
            }
        )
    }

    private fun emit(effect: MonthlyBudgetEffect) = viewModelScope.launch {
        _effect.emit(effect)
    }
}