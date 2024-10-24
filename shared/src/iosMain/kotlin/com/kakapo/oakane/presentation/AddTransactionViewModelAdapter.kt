package com.kakapo.oakane.presentation

import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.model.transaction.TransactionModel
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

    fun onClickButton(transaction: TransactionParam, transactionId: Long) {
        viewModel.onClickButton(transaction, transactionId)
    }

    fun observeState(onStateChange: (TransactionModel) -> Unit) {
        scope.launch {
            viewModel.transaction.collect { transaction ->
                Logger.d { "observeState $transaction" }
                onStateChange.invoke(transaction)
            }
        }
    }

}