package com.kakapo.oakane.presentation.viewModel.addTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.data.model.TransactionParam
import com.kakapo.data.repository.base.CategoryRepository
import com.kakapo.data.repository.base.TransactionRepository
import com.kakapo.model.transaction.TransactionType
import com.kakapo.oakane.domain.usecase.base.SaveTransactionUseCase
import com.kakapo.oakane.domain.usecase.base.UpdateTransactionUseCase
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("AddTransactionViewModelKt")
class AddTransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val saveTransactionUseCase: SaveTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase
) : ViewModel(){

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(AddTransactionState())

    @NativeCoroutines
    val uiSideEffect get() = _uiSideEffect.asSharedFlow()
    private val _uiSideEffect = MutableSharedFlow<AddTransactionEffect>()

    private var spentBefore: Double = 0.0
    private var walletId: Long = 0

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
            is AddTransactionEvent.ChangedAmount -> _uiState.update { it.copy(transactionAmount = event.value) }
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
        if (transactionId == 0L) create(transaction)
        else update(transaction)
    }

    private fun loadTransactionBy(id: Long) = viewModelScope.launch {
        transactionRepository.loadTransactionBy(id).fold(
            onSuccess = { transaction ->
                spentBefore = transaction.amount
                walletId = transaction.walletId
                _uiState.update { it.copy(transaction) }
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
        saveTransactionUseCase.execute(transaction).fold(
            onSuccess = {
                emit(AddTransactionEffect.NavigateBack)
            },
            onFailure = {
                Logger.e(throwable = it, messageString = "error create transaction ${it.message}")
            }
        )
    }

    private fun update(transaction: TransactionParam) = viewModelScope.launch {
        val transactionParam = transaction.copy(walletId = walletId)
        updateTransactionUseCase.executed(transactionParam, spentBefore).fold(
            onSuccess = { emit(AddTransactionEffect.NavigateBack) },
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