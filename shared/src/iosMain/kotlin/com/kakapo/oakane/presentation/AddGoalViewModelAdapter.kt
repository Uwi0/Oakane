package com.kakapo.oakane.presentation

import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalEffect
import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalEvent
import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalState
import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AddGoalViewModelAdapter(
    private val viewModel: AddGoalViewModel,
    private val scope: CoroutineScope
) {

    fun observeState(state: (AddGoalState) -> Unit) = scope.launch {
        viewModel.uiState.collect { state.invoke(it) }
    }

    fun observeEffect(effect: (AddGoalEffect) -> Unit) = scope.launch {
        viewModel.uiEffect.collect { effect.invoke(it) }
    }

    fun handleEvent(event: AddGoalEvent) {
        viewModel.handleEvent(event)
    }
}