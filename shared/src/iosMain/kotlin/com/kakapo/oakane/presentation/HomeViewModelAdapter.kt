package com.kakapo.oakane.presentation

import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.presentation.viewModel.home.HomeEffect
import com.kakapo.oakane.presentation.viewModel.home.HomeEvent
import com.kakapo.oakane.presentation.viewModel.home.HomeState
import com.kakapo.oakane.presentation.viewModel.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeViewModelAdapter(
    private val viewModel: HomeViewModel,
    private val scope: CoroutineScope
) {

    init {
        viewModel.initializeData()
    }

    fun initViewModel() = viewModel.initializeData()

    fun observeState(onStateChange: (HomeState) -> Unit) {
        scope.launch {
            viewModel.uiState.collect { state ->
                onStateChange.invoke(state)
            }
        }
    }

    fun observeEffect(onEffectChange: (HomeEffect) -> Unit) {
        scope.launch {
            viewModel.uiEffect.collect { effect ->
                onEffectChange.invoke(effect)
            }
        }
    }

    fun handle(event: HomeEvent) {
        viewModel.handleEvent(event)
    }

}