package com.kakapo.oakane.presentation.viewModel.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.common.intoMidnight
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.model.transaction.TransactionType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(TransactionsState())

    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<TransactionsEffect>()

    private val searchQuery = MutableStateFlow("")
    private val selectedType = MutableStateFlow<TransactionType?>(null)
    private val selectedDate = MutableStateFlow(0L)
    private val selectedCategory = MutableStateFlow<CategoryModel?>(null)

    fun initializeData() {
        loadTransactions()
        filterTransactions()
    }

    fun handleEvent(event: TransactionsEvent) {
        when (event) {
            is TransactionsEvent.FilterBy -> searchQuery.update { event.query }
            is TransactionsEvent.FilterByType -> {
                selectedType.update { event.value }
                hideSheet()
            }

            is TransactionsEvent.FilterByDate -> {
                selectedDate.update { event.timeMillis }
                hideSheet()
            }

            is TransactionsEvent.FilterByCategory -> {
                selectedCategory.update { event.value }
                hideSheet()
            }

            is TransactionsEvent.Delete -> delete(event.transaction)
            is TransactionsEvent.ShowSheet -> _uiState.update { it.showSheet(event.content) }
            is TransactionsEvent.ToDetail -> emit(TransactionsEffect.ToDetail(event.id))
            TransactionsEvent.HideSheet -> hideSheet()
            TransactionsEvent.NavigateBack -> emit(TransactionsEffect.NavigateBack)
        }
    }

    private fun filterTransactions() = viewModelScope.launch {
        combine(
            searchQuery,
            selectedType,
            selectedDate,
            selectedCategory,
            uiState.map { it.transactions }
        ) { query, type, date, category, transactions ->
            val timePredicate: (TransactionModel) -> Boolean = {
                if (date == 0L) true
                else it.dateCreated.intoMidnight() == date.intoMidnight()
            }

            _uiState.update {
                it.copy(
                    searchQuery = query,
                    selectedType = type,
                    selectedDate = date,
                    selectedCategory = category
                )
            }

            transactions
                .filter { it.title.contains(query, true) }
                .filter { if (type == null) true else it.type == type }
                .filter(timePredicate)
                .filter { if (category == null) true else it.category.id == category.id }
        }.collect { filteredTransactions ->
            _uiState.update { it.copy(filteredTransactions = filteredTransactions) }
        }
    }

    private fun delete(transaction: TransactionModel) = viewModelScope.launch {
        transactionRepository.deleteTransactionBy(transaction.id).fold(
            onSuccess = {
                updateDeleted(transaction)
            },
            onFailure = { e ->
                Logger.e(throwable = e, messageString = "error delete item ${e.message}")
            }
        )
    }

    private fun loadTransactions() = viewModelScope.launch {
        transactionRepository.loadTransactions().collect { result ->
            result.fold(
                onSuccess = { transactionsResult ->
                    _uiState.update { it.copy(transactions = transactionsResult) }
                },
                onFailure = { e ->
                    Logger.e(throwable = e, messageString = "error load transactions ${e.message}")
                }
            )
        }
    }

    private fun updateDeleted(transactions: TransactionModel) {
        val deletedTransaction = _uiState.value.filteredTransactions.toMutableList()
        deletedTransaction.remove(transactions)
        _uiState.update { it.copy(filteredTransactions = deletedTransaction) }
    }

    private fun hideSheet() {
        _uiState.update { it.hideSheet() }
        emit(TransactionsEffect.HideSheet)
    }

    private fun emit(effect: TransactionsEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }

}