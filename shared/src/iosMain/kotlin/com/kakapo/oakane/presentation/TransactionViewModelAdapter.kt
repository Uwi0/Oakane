package com.kakapo.oakane.presentation

import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.model.transaction.TransactionType
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TransactionViewModelAdapter(
    private val viewModel: TransactionsViewModel,
    private val scope: CoroutineScope
) {

    init {
        viewModel.initializeData()
    }

    fun observeState(onStateChange: (List<TransactionModel>) -> Unit) {
        scope.launch {
            viewModel.transactions.collect { transactions ->
                onStateChange.invoke(transactions)
            }
        }
    }

    fun filterTransactionsBy(query: String, type: TransactionType?, selectedDate: Long){
        viewModel.filterTransactionsBy(query, type, selectedDate)
    }
}