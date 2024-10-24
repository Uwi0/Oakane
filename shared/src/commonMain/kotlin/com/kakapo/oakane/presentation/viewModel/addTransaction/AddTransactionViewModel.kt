package com.kakapo.oakane.presentation.viewModel.addTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.transaction.TransactionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class AddTransactionViewModel(
    private val repository: TransactionRepository
) : ViewModel(), KoinComponent {

    val transaction get() = _transaction.asStateFlow()
    private val _transaction = MutableStateFlow(TransactionModel())

    fun initializeData(id: Long) {
        loadTransactionBy(id)
    }

    fun onClickButton(transaction: TransactionParam, transactionId: Long) {
        if (transactionId == 0L) {
            save(transaction)
        } else {
            update(transaction, transactionId)
        }
    }

    private fun loadTransactionBy(id: Long) = viewModelScope.launch {
        repository.loadTransactionBy(id).fold(
            onSuccess = { transaction ->
                _transaction.update { transaction }
            },
            onFailure = {
                Logger.e(throwable = it, messageString = "error load transaction ${it.message}")
            }
        )
    }

    private fun save(transaction: TransactionParam) = viewModelScope.launch {
        repository.save(transaction)
    }

    private fun update(transaction: TransactionParam, transactionId: Long) = viewModelScope.launch {
        repository.update(transaction, transactionId).fold(
            onSuccess = {
                Logger.d { "success update transaction $transaction" }
            },
            onFailure = {
                Logger.e(throwable = it, messageString = "error update transaction ${it.message}")
            }
        )
    }

}