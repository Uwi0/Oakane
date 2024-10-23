package com.kakapo.oakane.presentation.viewModel.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.transaction.TransactionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    val transaction get() = _transaction.asStateFlow()
    private val _transaction = MutableStateFlow(TransactionModel())

    fun initializeData(id: Long) {
        loadTransactionBy(id)
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
}