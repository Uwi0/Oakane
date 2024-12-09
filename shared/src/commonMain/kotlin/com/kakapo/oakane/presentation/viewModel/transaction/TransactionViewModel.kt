package com.kakapo.oakane.presentation.viewModel.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.domain.usecase.base.DeleteTransactionUseCase
import com.kakapo.oakane.model.transaction.TransactionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val repository: TransactionRepository,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {

    val transaction get() = _transaction.asStateFlow()
    private val _transaction = MutableStateFlow(TransactionModel())

    fun initializeData(id: Long) {
        loadTransactionBy(id)
    }

    fun deleteTransactionBy() = viewModelScope.launch {
        deleteTransactionUseCase.execute(transaction.value).fold(
            onSuccess = {
                Logger.d { "success delete transaction" }
            },
            onFailure = {
                Logger.e(throwable = it, messageString = "error delete transaction ${it.message}")
            }
        )
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