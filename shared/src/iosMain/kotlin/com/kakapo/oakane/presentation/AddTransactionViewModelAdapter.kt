package com.kakapo.oakane.presentation

import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionEvent
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionState
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AddTransactionViewModelAdapter(
    private val viewModel: AddTransactionViewModel,
    private val scope: CoroutineScope
) {
    fun initializeData(id: Long) {
        viewModel.initializeData(id)
    }

    fun handleEvent(event: AddTransactionEvent) {
        viewModel.handleEvent(event)
    }

    fun observeState(onStateChange: (AddTransactionState) -> Unit) {
        scope.launch {
            viewModel.uiState.collect { uiState ->
                onStateChange.invoke(uiState)
            }
        }
    }
}