package com.kakapo.oakane.presentation

import com.kakapo.oakane.presentation.viewModel.goals.GoalsEffect
import com.kakapo.oakane.presentation.viewModel.goals.GoalsEvent
import com.kakapo.oakane.presentation.viewModel.goals.GoalsState
import com.kakapo.oakane.presentation.viewModel.goals.GoalsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GoalsViewModelAdapter(
    private val viewModel: GoalsViewModel,
    private val scope: CoroutineScope
) {

    init {
        viewModel.initData()
    }

    fun observeState(uiState: (GoalsState) -> Unit) = scope.launch {
        viewModel.uiState.collect { state ->
            uiState.invoke(state)
        }
    }

    fun observeEffect(uiEffect: (GoalsEffect) -> Unit) = scope.launch {
        viewModel.uiEffect.collect { effect ->
            uiEffect.invoke(effect)
        }
    }

    fun handle(event: GoalsEvent) = viewModel.handleEvent(event)
}