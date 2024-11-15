package com.kakapo.oakane.presentation

import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.model.transaction.asTransactionType
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

    fun observeState(onStateChange: (List<TransactionModel>) -> Unit) {
        scope.launch {
            viewModel.uiState.collect { uiState->
                onStateChange.invoke(uiState.filteredTransactions)
            }
        }
    }

    fun filterTransactionsBy(query: String, type: String, selectedDate: Long) {

    }

    fun deleteTransaction(item: TransactionModel) {

    }
}