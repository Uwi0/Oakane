package com.kakapo.oakane.presentation.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.transaction.TransactionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    val transactions get() = _transactions.asStateFlow()
    private val _transactions: MutableStateFlow<List<TransactionModel>> = MutableStateFlow(emptyList())

    fun initializeData() {
        loadRecentTransactions()
    }

    private fun loadRecentTransactions() = viewModelScope.launch {
        repository.loadRecentTransactions().collect { result ->
            result.fold(
                onSuccess = { transactionsResult ->
                    _transactions.update { transactionsResult }
                    Logger.d { "success load transaction $transactionsResult" }
                },
                onFailure = { e ->
                    Logger.e(messageString = "error load recent transaction ${e.message}")
                }
            )
        }
    }
}