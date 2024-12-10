package com.kakapo.oakane.presentation

import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetEffect
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetEvent
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetState
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MonthlyBudgetViewModelAdapter(
    private val viewModel: MonthlyBudgetViewModel,
    private val scope: CoroutineScope
) {

    fun initData(){
        viewModel.initializeData()
    }

    fun observeState(onStateChange: (MonthlyBudgetState) -> Unit) = scope.launch {
        viewModel.uiState.collect { state ->
            onStateChange.invoke(state)
        }
    }

    fun observeEffect(onEffectChange: (MonthlyBudgetEffect) -> Unit) = scope.launch {
        viewModel.effect.collect { effect ->
            onEffectChange.invoke(effect)
        }
    }

    fun handleEvent(event: MonthlyBudgetEvent) = viewModel.handleEvent(event)
}