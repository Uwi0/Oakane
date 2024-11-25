package com.kakapo.oakane.presentation

import com.kakapo.oakane.presentation.viewModel.goal.GoalEffect
import com.kakapo.oakane.presentation.viewModel.goal.GoalEvent
import com.kakapo.oakane.presentation.viewModel.goal.GoalState
import com.kakapo.oakane.presentation.viewModel.goal.GoalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GoalViewModelAdapter(
    private val viewModel: GoalViewModel,
    private val scope: CoroutineScope
) {


    fun initViewModel(goalId: Long) {
        viewModel.initializeData(goalId)
    }

    fun observeUiState(state: (GoalState) -> Unit) = scope.launch {
        viewModel.uiState.collect { uiState ->
            state.invoke(uiState)
        }
    }

    fun observeUiEffect(effect: (GoalEffect) -> Unit) = scope.launch {
        viewModel.uiEffect.collect { uiEffect ->
            effect.invoke(uiEffect)
        }
    }

    fun handleEvent(event: GoalEvent) {
        viewModel.handleEvent(event)
    }
}