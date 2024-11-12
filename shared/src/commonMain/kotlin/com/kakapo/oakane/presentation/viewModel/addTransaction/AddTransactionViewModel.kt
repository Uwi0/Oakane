package com.kakapo.oakane.presentation.viewModel.addTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.transaction.TransactionModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class AddTransactionViewModel(
    private val repository: TransactionRepository
) : ViewModel(), KoinComponent {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(AddTransactionState())

    val uiSideEffect get() = _uiSideEffect.asSharedFlow()
    private val _uiSideEffect = MutableSharedFlow<AddTransactionEffect>()

    val transaction get() = _transaction.asStateFlow()
    private val _transaction = MutableStateFlow(TransactionModel())

    fun initializeData(id: Long) {
        loadTransactionBy(id)
    }

    fun handleEvent(event: AddTransactionEvent) {
        when (event) {
            is AddTransactionEvent.ChangeNote -> _uiState.update { it.copy(note = event.value) }
            is AddTransactionEvent.ChangedAmount -> _uiState.update { it.copy(amount = event.value) }
            is AddTransactionEvent.ChangedTitle -> _uiState.update { it.copy(title = event.value) }
            is AddTransactionEvent.DropDownTypeIs -> _uiState.update { it.dropDownType(event.expanded) }
            is AddTransactionEvent.ChangeType -> _uiState.update { it.copy(transactionType = event.value) }
            is AddTransactionEvent.Dialog -> _uiState.update { it.copy(isShowDialog = event.shown) }
            is AddTransactionEvent.ChangeDate -> _uiState.update { it.copy(date = event.value) }
            AddTransactionEvent.NavigateBack -> emit(AddTransactionEffect.NavigateBack)
            AddTransactionEvent.SaveTransaction -> TODO()
        }
    }

    fun onClickButton(transaction: TransactionParam, transactionId: Long) {
        if (transactionId == 0L) {
            create(transaction)
        } else {
            update(transaction, transactionId)
        }
    }

    private fun loadTransactionBy(id: Long) = viewModelScope.launch {
        repository.loadTransactionBy(id).fold(
            onSuccess = { transaction ->
                _uiState.update { it.copy(transaction) }
                _transaction.update { transaction }
            },
            onFailure = {
                Logger.e(throwable = it, messageString = "error load transaction ${it.message}")
            }
        )
    }

    private fun create(transaction: TransactionParam) = viewModelScope.launch {
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

    private fun emit(effect: AddTransactionEffect) = viewModelScope.launch {
        _uiSideEffect.emit(effect)
    }

}