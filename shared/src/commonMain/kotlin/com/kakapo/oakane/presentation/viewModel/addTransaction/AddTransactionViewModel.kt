package com.kakapo.oakane.presentation.viewModel.addTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.data.repository.base.CategoryRepository
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.transaction.TransactionType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class AddTransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel(), KoinComponent {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(AddTransactionState())

    val uiSideEffect get() = _uiSideEffect.asSharedFlow()
    private val _uiSideEffect = MutableSharedFlow<AddTransactionEffect>()

    fun initializeData(id: Long) {
        loadCategories().invokeOnCompletion {
            if (id != 0L) {
                loadTransactionBy(id)
            } else {
                setDefaultCategory()
            }
        }


    }

    fun handleEvent(event: AddTransactionEvent) {
        when (event) {
            is AddTransactionEvent.ChangeNote -> _uiState.update { it.copy(note = event.value) }
            is AddTransactionEvent.ChangedAmount -> _uiState.update { it.copy(amount = event.value) }
            is AddTransactionEvent.ChangedTitle -> _uiState.update { it.copy(title = event.value) }
            is AddTransactionEvent.DropDownTypeIs -> _uiState.update { it.dropDownType(event.expanded) }
            is AddTransactionEvent.ChangeType -> updateTransactionType(value = event.value)
            is AddTransactionEvent.Dialog -> _uiState.update { it.copy(isShowDialog = event.shown) }
            is AddTransactionEvent.ChangeDate -> _uiState.update { it.update(date = event.value) }
            is AddTransactionEvent.Sheet -> _uiState.update { it.copy(sheetShown = event.shown) }
            is AddTransactionEvent.SetCategory -> _uiState.update { it.update(category = event.value) }
            AddTransactionEvent.NavigateBack -> emit(AddTransactionEffect.NavigateBack)
            AddTransactionEvent.SaveTransaction -> onClickButton()
        }
    }

    private fun onClickButton() {
        val transaction = uiState.value.asTransactionParam()
        val transactionId = uiState.value.transactionId
        if (transactionId == 0L) {
            create(transaction)
        } else {
            update(transaction, transactionId)
        }
    }

    private fun loadTransactionBy(id: Long) = viewModelScope.launch {
        transactionRepository.loadTransactionBy(id).fold(
            onSuccess = { transaction ->
                _uiState.update { it.copy(transaction) }
                Logger.d{"success load transaction $transaction"}
            },
            onFailure = {
                Logger.e(throwable = it, messageString = "error load transaction ${it.message}")
            }
        )
    }

    private fun loadCategories() = viewModelScope.launch {
        categoryRepository.loadCategories().collect { resultCategories ->
            resultCategories.fold(
                onSuccess = { categories ->
                    _uiState.update { it.copy(categories = categories) }
                },
                onFailure = {
                    Logger.e(throwable = it, messageString = "error load categories ${it.message}")
                }
            )
        }
    }

    private fun create(transaction: TransactionParam) = viewModelScope.launch {
        transactionRepository.save(transaction).fold(
            onSuccess = {
                Logger.d { "success create transaction $transaction" }
                emit(AddTransactionEffect.NavigateBack)
            },
            onFailure = {
                Logger.e(throwable = it, messageString = "error create transaction ${it.message}")
            }
        )
    }

    private fun update(transaction: TransactionParam, transactionId: Long) = viewModelScope.launch {
        transactionRepository.update(transaction, transactionId).fold(
            onSuccess = {
                Logger.d { "success update transaction $transaction" }
                emit(AddTransactionEffect.NavigateBack)
            },
            onFailure = {
                Logger.e(throwable = it, messageString = "error update transaction ${it.message}")
            }
        )
    }

    private fun updateTransactionType(value: TransactionType) {
        val categories = _uiState.value.categories
        val category = _uiState.value.category
        val defaultIncome = categories.first { it.type == TransactionType.Income }
        val defaultExpense = categories.first { it.type == TransactionType.Expense }
        val defaultCategory = if (value == TransactionType.Income) defaultIncome else defaultExpense

        if (category.type == value) {
            _uiState.update { it.copy(transactionType = value, isDropdownExpanded = false) }
        } else {
            _uiState.update {
                it.copy(
                    category = defaultCategory,
                    transactionType = value,
                    isDropdownExpanded = false
                )
            }
        }
    }

    private fun setDefaultCategory() {
        val currentType = uiState.value.transactionType
        val categories = uiState.value.categories
        val defaultCategory = categories.first { it.type == currentType }
        _uiState.update { it.copy(category = defaultCategory) }
    }

    private fun emit(effect: AddTransactionEffect) = viewModelScope.launch {
        _uiSideEffect.emit(effect)
    }



}