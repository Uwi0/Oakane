package com.kakapo.oakane.presentation

import com.kakapo.oakane.presentation.viewModel.transaction.TransactionEvent
import com.kakapo.oakane.presentation.viewModel.transaction.TransactionState
import com.kakapo.oakane.presentation.viewModel.transaction.TransactionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TransactionViewModelAdapter(
    private val viewModel: TransactionViewModel,
    private val scope: CoroutineScope
) {

    fun initializeData(transactionId: Long) {
        viewModel.initializeData(transactionId)
    }

    fun observeData(onStateChange: (TransactionState) -> Unit) {
        scope.launch {
            viewModel.uiState.collect { transaction ->
                onStateChange.invoke(transaction)
            }
        }
    }

    fun handle(event: TransactionEvent) {
        viewModel.handleEvent(event)
    }
}