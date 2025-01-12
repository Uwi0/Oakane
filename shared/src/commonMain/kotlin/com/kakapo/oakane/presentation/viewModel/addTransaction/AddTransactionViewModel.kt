package com.kakapo.oakane.presentation.viewModel.addTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.model.TransactionParam
import com.kakapo.data.repository.base.CategoryRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.data.repository.base.TransactionRepository
import com.kakapo.domain.usecase.base.SaveTransactionUseCase
import com.kakapo.domain.usecase.base.UpdateTransactionUseCase
import com.kakapo.model.category.CategoryModel
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.model.transaction.TransactionType
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
    private val systemRepository: SystemRepository,
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
        loadCurrency()
        loadCategories().invokeOnCompletion {
            if (id != 0L) loadTransactionBy(id)
            else setDefaultCategory()
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
        val onSuccess : (TransactionModel) -> Unit = { transaction ->
            spentBefore = transaction.amount
            walletId = transaction.walletId
            _uiState.update { it.copy(transaction) }
        }
        transactionRepository.loadTransactionBy(id).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun loadCategories() = viewModelScope.launch {
        val onSuccess: (List<CategoryModel>) -> Unit = { categories ->
            _uiState.update { it.copy(categories = categories) }
        }
        categoryRepository.loadCategories().collect { resultCategories ->
            resultCategories.fold(
                onSuccess = onSuccess,
                onFailure = ::handleError
            )
        }
    }

    private fun loadCurrency() = viewModelScope.launch {
        systemRepository.loadSavedCurrency().fold(
            onSuccess = { currency -> _uiState.update { it.copy(currency = currency) } },
            onFailure = ::handleError
        )
    }

    private fun create(transaction: TransactionParam) = viewModelScope.launch {
        saveTransactionUseCase.execute(transaction).fold(
            onSuccess = {
                emit(AddTransactionEffect.NavigateBack)
            },
            onFailure = ::handleError
        )
    }

    private fun update(transaction: TransactionParam) = viewModelScope.launch {
        val transactionParam = transaction.copy(walletId = walletId)
        updateTransactionUseCase.executed(transactionParam, spentBefore).fold(
            onSuccess = { emit(AddTransactionEffect.NavigateBack) },
            onFailure = ::handleError
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

    private fun handleError(throwable: Throwable?) {
        emit(AddTransactionEffect.ShowError(throwable?.message ?: "Unknown Error"))
    }

    private fun emit(effect: AddTransactionEffect) = viewModelScope.launch {
        _uiSideEffect.emit(effect)
    }
}