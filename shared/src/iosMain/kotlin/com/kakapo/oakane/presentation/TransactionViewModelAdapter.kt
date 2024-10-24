package com.kakapo.oakane.presentation

import com.kakapo.oakane.model.transaction.TransactionModel
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

    fun deleteTransactionBy(id: Long) {
        viewModel.deleteTransactionBy(id)
    }

    fun observeData(onStateChange: (TransactionModel) -> Unit) {
        scope.launch {
            viewModel.transaction.collect { transaction ->
                onStateChange.invoke(transaction)
            }
        }
    }
}