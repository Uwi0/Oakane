package com.kakapo.oakane.presentation.viewModel.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.transaction.TransactionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val transactions get() = _transactions.asStateFlow()
    private val _transactions = MutableStateFlow<List<TransactionModel>>(emptyList())

    fun initializeData() {
        loadTransactions()
    }

    private fun loadTransactions() = viewModelScope.launch {
        transactionRepository.loadRecentTransactions().collect { result ->
            result.fold(
                onSuccess = { transactionsResult ->
                    _transactions.update { transactionsResult }
                },
                onFailure = { e ->
                    Logger.e(throwable = e, messageString = "error load transactions ${e.message}")
                }
            )
        }
    }
}