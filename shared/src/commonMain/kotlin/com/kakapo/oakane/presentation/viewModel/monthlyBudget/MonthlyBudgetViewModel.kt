package com.kakapo.oakane.presentation.viewModel.monthlyBudget

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MonthlyBudgetViewModel: ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(MonthlyBudgetState())

    fun handleEvent(event: MonthlyBudgetEvent) {
        when(event){
            is MonthlyBudgetEvent.Changed -> _uiState.update { it.copy(amount = event.amount) }
        }
    }
}