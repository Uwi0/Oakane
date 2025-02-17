package com.kakapo.oakane.presentation.viewModel.monthlyBudget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.common.asCustomResult
import com.kakapo.common.subscribe
import com.kakapo.data.model.CategoryLimitParam
import com.kakapo.data.model.MonthlyBudgetParam
import com.kakapo.data.repository.base.CategoryLimitRepository
import com.kakapo.data.repository.base.CategoryRepository
import com.kakapo.data.repository.base.MonthlyBudgetRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.domain.usecase.base.SetRecurringBudgetUseCase
import com.kakapo.domain.usecase.base.ValidateCategoryLimitUseCase
import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryLimitModel
import com.kakapo.model.category.CategoryModel
import com.kakapo.model.monthlyBudget.MonthlyBudgetModel
import com.kakapo.model.system.Theme
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("MonthlyBudgetViewModelKt")
class MonthlyBudgetViewModel(
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val categoryRepository: CategoryRepository,
    private val categoryLimitRepository: CategoryLimitRepository,
    private val systemRepository: SystemRepository,
    private val validateCategoryLimitUseCase: ValidateCategoryLimitUseCase,
    private val setRecurringBudgetUseCase: SetRecurringBudgetUseCase
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(MonthlyBudgetState())

    @NativeCoroutines
    val effect get() = _effect.asSharedFlow()
    private val _effect = MutableSharedFlow<MonthlyBudgetEffect>()

    fun initializeData() {
        checkTableIsNotEmpty()
        loadExpenseCategory()
        loadCurrency()
        loadTheme()
    }

    fun handleEvent(event: MonthlyBudgetEvent) {
        when (event) {
            MonthlyBudgetEvent.NavigateBack -> emit(MonthlyBudgetEffect.NavigateBack)
            MonthlyBudgetEvent.Save -> saveBudget()
            is MonthlyBudgetEvent.Changed -> _uiState.update { it.copy(amount = event.amount) }
            is MonthlyBudgetEvent.Dialog -> _uiState.update { it.dialog(event.shown) }
            is MonthlyBudgetEvent.CreateCategoryLimitBy -> saveCategoryLimit(event)
            is MonthlyBudgetEvent.Selected -> _uiState.update { it.showDialog(event.categoryLimit) }
        }
    }

    private fun checkTableIsNotEmpty() = viewModelScope.launch {
        val onSuccess: (Boolean) -> Unit = { tableNotEmpty ->
            _uiState.update { it.copy(isEditMode = tableNotEmpty) }
            if (tableNotEmpty) {
                loadMonthlyBudget()
            }
        }
        monthlyBudgetRepository.hasCurrentMonthlyBudgetAtTheTime().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun loadMonthlyBudget() = viewModelScope.launch {
        val onSuccess: (MonthlyBudgetModel) -> Unit = { monthlyBudget ->
            _uiState.update { it.copy(monthlyBudget) }
            loadCategoryLimitsWith(monthlyBudget.id)
        }
        monthlyBudgetRepository.loadMonthlyBudget().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun loadExpenseCategory() = viewModelScope.launch {
        val onSuccess: (List<CategoryModel>) -> Unit = { category ->
            _uiState.update { it.copy(expenseCategories = category) }
        }
        categoryRepository.loadExpenseCategories().collect { result ->
            result.fold(
                onSuccess = onSuccess,
                onFailure = ::handleError
            )
        }
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

    private fun loadTheme() = viewModelScope.launch {
        val onSuccess: (Theme) -> Unit = { theme ->
            _uiState.update { it.copy(theme = theme) }
        }
        systemRepository.loadSavedTheme().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun saveBudget() {
        val monthlyBudget = uiState.value.asMonthlyBudgetParam()
        val isEditMode = uiState.value.isEditMode
        if (isEditMode) update(monthlyBudget)
        else add(monthlyBudget)
        saveRecurringBudget()
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

    private fun saveCategoryLimit(event: CreateCategoryLimit) = viewModelScope.launch {
        val isEditMode = uiState.value.selectedCategoryLimit != null
        val categoryLimit = CategoryLimitParam(
            id = uiState.value.selectedCategoryLimit?.id ?: 0,
            categoryId = event.categoryId,
            monthlyBudgetId = uiState.value.id,
            limitAmount = event.limitAmount
        )

        val onSuccess: (Unit) -> Unit = { _ ->
            _uiState.update { it.dialog(shown = false) }
            loadCategoryLimitsWith(uiState.value.id)
        }

        validateCategoryLimitUseCase.execute(categoryLimit, isEditMode).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun loadCategoryLimitsWith(monthlyBudgetId: Long) = viewModelScope.launch {
        val onSuccess: (List<CategoryLimitModel>) -> Unit = { categoryLimits ->
            _uiState.update { it.copy(categoryLimits = categoryLimits) }
        }
        categoryLimitRepository.loadCategoryLimitsBy(monthlyBudgetId)
            .asCustomResult()
            .subscribe(
                onSuccess = onSuccess,
                onError = ::handleError
            )
    }

    private fun saveRecurringBudget() = viewModelScope.launch {
        val budgets = uiState.value.asMonthlyBudgetReCurring()
        val categoryLimits = uiState.value.asCategoryLimitRecurring()
        setRecurringBudgetUseCase.execute(budgets, categoryLimits).fold(
            onSuccess = { Logger.d("Recurring budget saved") },
            onFailure = ::handleError
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