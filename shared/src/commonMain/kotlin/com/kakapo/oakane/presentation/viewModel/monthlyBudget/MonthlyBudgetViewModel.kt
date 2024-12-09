package com.kakapo.oakane.presentation.viewModel.monthlyBudget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.common.asCustomResult
import com.kakapo.oakane.common.subscribe
import com.kakapo.oakane.data.model.CategoryLimitParam
import com.kakapo.oakane.data.model.MonthlyBudgetParam
import com.kakapo.oakane.data.repository.base.CategoryLimitRepository
import com.kakapo.oakane.data.repository.base.CategoryRepository
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import com.kakapo.oakane.domain.usecase.base.ValidateCategoryLimitUseCase
import com.kakapo.oakane.model.MonthlyBudgetModel
import com.kakapo.oakane.model.category.CategoryLimitModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MonthlyBudgetViewModel(
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val categoryRepository: CategoryRepository,
    private val categoryLimitRepository: CategoryLimitRepository,
    private val validateCategoryLimitUseCase: ValidateCategoryLimitUseCase
) : ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(MonthlyBudgetState())

    val effect get() = _effect.asSharedFlow()
    private val _effect = MutableSharedFlow<MonthlyBudgetEffect>()

    fun initializeData() {
        checkTableIsNotEmpty()
        loadExpenseCategory()
    }

    fun handleEvent(event: MonthlyBudgetEvent) {
        when (event) {
            MonthlyBudgetEvent.NavigateBack -> emit(MonthlyBudgetEffect.NavigateBack)
            MonthlyBudgetEvent.Save -> saveBudget()
            is MonthlyBudgetEvent.Changed -> _uiState.update { it.copy(amount = event.amount) }
            is MonthlyBudgetEvent.Dialog -> _uiState.update { it.dialog(event.shown) }
            is MonthlyBudgetEvent.CreateCategoryLimitBy -> validateCreateCategoryLimit(event)
            is MonthlyBudgetEvent.Selected -> _uiState.update { it.showDialog(event.categoryLimit) }
        }
    }

    private fun checkTableIsNotEmpty() = viewModelScope.launch {
        monthlyBudgetRepository.tableNotEmpty().fold(
            onSuccess = { tableNotEmpty ->
                _uiState.update { it.copy(isEditMode = tableNotEmpty) }
                loadMonthlyBudget()
                loadCategoryLimits()
            },
            onFailure = {
                Logger.e(messageString = it.message.toString())
            }
        )
    }

    private fun loadMonthlyBudget() = viewModelScope.launch {
        val onSuccess: (MonthlyBudgetModel) -> Unit = { monthlyBudget ->
            val totalBudget = monthlyBudget.totalBudget.toInt().toString()
            _uiState.update { it.copy(amount = totalBudget, id = monthlyBudget.id) }
        }
        monthlyBudgetRepository.loadMonthlyBudget().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun loadExpenseCategory() = viewModelScope.launch {
        categoryRepository.loadExpenseCategories().collect { result ->
            result.fold(
                onSuccess = { categories ->
                    _uiState.update { it.copy(expenseCategories = categories) }
                },
                onFailure = ::handleError
            )
        }
    }

    private fun saveBudget() {
        val monthlyBudget = uiState.value.asMonthlyBudgetParam()
        val isEditMode = uiState.value.isEditMode
        if (isEditMode) update(monthlyBudget)
        else add(monthlyBudget)
    }

    private fun add(monthlyBudget: MonthlyBudgetParam) = viewModelScope.launch {
        monthlyBudgetRepository.add(monthlyBudget).fold(
            onSuccess = { emit(MonthlyBudgetEffect.NavigateBack) },
            onFailure = ::handleError
        )
    }

    private fun update(monthlyBudget: MonthlyBudgetParam) = viewModelScope.launch {
        monthlyBudgetRepository.update(monthlyBudget).fold(
            onSuccess = { emit(MonthlyBudgetEffect.NavigateBack) },
            onFailure = ::handleError
        )
    }

    private fun validateCreateCategoryLimit(event: CreateCategoryLimit) = viewModelScope.launch {
        val monthlyBudgetId = uiState.value.id
        val categoryId = event.categoryId
        val limitAmount = event.limitAmount
        val onSuccess: (Boolean) -> Unit = { isValid ->
            if (isValid) {
                createCategoryLimitBy(categoryId, limitAmount)
            } else {
                emit(MonthlyBudgetEffect.ShowError("Limit amount must be less than total budget"))
            }
        }
        validateCategoryLimitUseCase.execute(monthlyBudgetId, limitAmount).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun createCategoryLimitBy(categoryId: Long, limitAmount: Double) =
        viewModelScope.launch {
            val monthlyBudgetId = uiState.value.id
            val categoryLimit = CategoryLimitParam(categoryId, monthlyBudgetId, limitAmount)
            categoryLimitRepository.save(categoryLimit).fold(
                onSuccess = { _uiState.update { it.copy(dialogShown = false) } },
                onFailure = ::handleError
            )
        }

    private fun loadCategoryLimits() = viewModelScope.launch {
        val monthlyBudgetId = uiState.value.id
        val onSuccess: (List<CategoryLimitModel>) -> Unit = { categoryLimits ->
            Logger.d(messageString = categoryLimits.toString())
            _uiState.update { it.copy(categoryLimits = categoryLimits) }
        }
        categoryLimitRepository.loadCategoryLimitsBy(monthlyBudgetId)
            .asCustomResult()
            .subscribe(
                onSuccess = onSuccess,
                onError = ::handleError
            )
    }

    private fun handleError(throwable: Throwable?) {
        val message = throwable?.message ?: "Unknown error"
        Logger.e(messageString = message)
        emit(MonthlyBudgetEffect.ShowError(message))
    }

    private fun emit(effect: MonthlyBudgetEffect) = viewModelScope.launch {
        _effect.emit(effect)
    }
}