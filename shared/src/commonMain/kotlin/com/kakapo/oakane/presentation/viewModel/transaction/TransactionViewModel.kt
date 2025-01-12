package com.kakapo.oakane.presentation.viewModel.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.data.repository.base.TransactionRepository
import com.kakapo.domain.usecase.base.DeleteTransactionUseCase
import com.kakapo.model.Currency
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("TransactionViewModelKt")
class TransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val systemRepository: SystemRepository,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(TransactionState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<TransactionEffect>()

    fun initializeData(id: Long) {
        loadTransactionBy(id)
        loadCurrency()
    }

    fun handleEvent(event: TransactionEvent) {
        val id = uiState.value.transaction.id
        when (event) {
            is TransactionEvent.DeleteTransaction -> deleteTransactionBy()
            is TransactionEvent.EditTransaction -> emit(TransactionEffect.EditTransactionBy(id))
            TransactionEvent.NavigateBack -> emit(TransactionEffect.NavigateBack)
        }
    }

    private fun deleteTransactionBy() = viewModelScope.launch {
        val transaction = uiState.value.transaction
        deleteTransactionUseCase.execute(transaction).fold(
            onSuccess = { emit(TransactionEffect.NavigateBack) },
            onFailure = ::handleError
        )
    }

    private fun loadTransactionBy(id: Long) = viewModelScope.launch {
        transactionRepository.loadTransactionBy(id).fold(
            onSuccess = { transaction -> _uiState.update { it.copy(transaction = transaction) } },
            onFailure = ::handleError
        )
    }

    private fun loadCurrency() = viewModelScope.launch {
        val onSuccess: (Currency) -> Unit = { currency ->
            _uiState.update { it.copy(currency = currency) }
        }
        systemRepository.loadSavedCurrency().fold(
            onSuccess = onSuccess,
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