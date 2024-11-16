package com.kakapo.oakane.presentation

import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsEffect
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsEvent
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsState
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TransactionsViewModelAdapter(
    private val viewModel: TransactionsViewModel,
    private val scope: CoroutineScope
) {

    init {
        viewModel.initializeData()
    }

    fun initializeData() {
        viewModel.initializeData()
    }

    fun observeState(onStateChange: (TransactionsState) -> Unit) {
        scope.launch {
            viewModel.uiState.collect { uiState->
                onStateChange.invoke(uiState)
            }
        }
    }

    fun observeEffect(onEffectChange: (TransactionsEffect) -> Unit) {
        scope.launch {
            viewModel.uiEffect.collect { effect ->
                onEffectChange.invoke(effect)
            }
        }
    }

    fun handleEvent(event: TransactionsEvent){
        viewModel.handleEvent(event)
    }
}