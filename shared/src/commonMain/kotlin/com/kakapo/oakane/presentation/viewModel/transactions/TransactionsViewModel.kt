package com.kakapo.oakane.presentation.viewModel.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.model.transaction.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val transactions get() = _filteredTransactions.asStateFlow()
    private val _filteredTransactions = MutableStateFlow<List<TransactionModel>>(emptyList())
    private val _transactions = MutableStateFlow<List<TransactionModel>>(emptyList())

    fun initializeData() {
        loadTransactions()
    }

    fun filterTransactionsBy(query: String, type: TransactionType?, selectedDate: Long) {
        val transactions = _transactions.value
            .filter { it.title.contains(query, true) }
            .filter { if (type == null) true else it.type == type }
            .filter { if (selectedDate == 0L) true else it.dateCreated == selectedDate }
        _filteredTransactions.update { transactions }
    }

    private fun loadTransactions() = viewModelScope.launch {
        transactionRepository.loadRecentTransactions().collect { result ->
            result.fold(
                onSuccess = { transactionsResult ->
                    _transactions.update { transactionsResult }
                    _filteredTransactions.update { transactionsResult }
                },
                onFailure = { e ->
                    Logger.e(throwable = e, messageString = "error load transactions ${e.message}")
                }
            )
        }
    }
}