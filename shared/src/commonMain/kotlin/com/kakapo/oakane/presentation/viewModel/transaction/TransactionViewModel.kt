package com.kakapo.oakane.presentation.viewModel.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.domain.usecase.base.DeleteTransactionUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val repository: TransactionRepository,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(TransactionState())

    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<TransactionEffect>()

    fun initializeData(id: Long) {
        loadTransactionBy(id)
    }

    fun handleEvent(event: TransactionEvent) {
        val id = uiState.value.transaction.id
        when (event) {
            is TransactionEvent.DeleteTransaction -> deleteTransactionBy()
            is TransactionEvent.EditTransaction -> emit(TransactionEffect.EditTransactionBy(id))
            TransactionEvent.NavigateBack -> emit(TransactionEffect.NavigateBack)
        }
    }

    fun deleteTransactionBy() = viewModelScope.launch {
        val transaction = uiState.value.transaction
        deleteTransactionUseCase.execute(transaction).fold(
            onSuccess = { emit(TransactionEffect.NavigateBack) },
            onFailure = ::handleError
        )
    }

    private fun loadTransactionBy(id: Long) = viewModelScope.launch {
        repository.loadTransactionBy(id).fold(
            onSuccess = { transaction -> _uiState.update { it.copy(transaction = transaction) } },
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        val errorMessage = throwable?.message ?: "Unknown error"
        Logger.e(throwable = throwable, messageString = "TransactionViewModel $errorMessage")
        emit(TransactionEffect.ShowError(errorMessage))
    }

    private fun emit(effect: TransactionEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}