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

    fun observeState(onStateChange: (List<TransactionModel>) -> Unit) {
        scope.launch {
            viewModel.transactions.collect { transactions ->
                onStateChange.invoke(transactions)
            }
        }
    }

    fun filterTransactionsBy(query: String, type: String, selectedDate: Long){
        val transactionType = if (type.isEmpty()) null else type.asTransactionType()
        viewModel.filterTransactionsBy(query, transactionType, selectedDate)
    }

    fun deleteTransaction(item: TransactionModel) {
        viewModel.deleteTransaction(item)
    }
}