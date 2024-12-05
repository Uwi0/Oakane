package com.kakapo.oakane.presentation.viewModel.monthlyBudget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.model.MonthlyBudgetParam
import com.kakapo.oakane.data.repository.base.CategoryRepository
import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MonthlyBudgetViewModel(
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val categoryRepository: CategoryRepository
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
            is MonthlyBudgetEvent.Dialog -> _uiState.update { it.copy(dialogShown = event.shown) }
        }
    }

    private fun checkTableIsNotEmpty() = viewModelScope.launch {
        monthlyBudgetRepository.tableNotEmpty().fold(
            onSuccess = { tableNotEmpty ->
                _uiState.update { it.copy(isEditMode = tableNotEmpty) }
                loadMonthlyBudget()
            },
            onFailure = {
                Logger.e(messageString = it.message.toString())
            }
        )
    }

    private fun loadMonthlyBudget() = viewModelScope.launch {
        monthlyBudgetRepository.loadMonthlyBudget().fold(
            onSuccess = { monthlyBudget ->
                _uiState.update {
                    it.copy(
                        amount = monthlyBudget.totalBudget.toInt().toString(),
                        id = monthlyBudget.id
                    )
                }
            },
            onFailure = {
                Logger.e(messageString = it.message.toString())
            }
        )
    }

    private fun loadExpenseCategory() = viewModelScope.launch {
        categoryRepository.loadExpenseCategories().collect { result ->
            result.fold(
                onSuccess = { categories ->
                    _uiState.update { it.copy(expenseCategories = categories) }
                },
                onFailure = {
                    Logger.e(messageString = it.message.toString())
                }
            )
        }
    }

    private fun saveBudget() {
        val monthlyBudget = uiState.value.asMonthlyBudgetParam()
        val isEditMode = uiState.value.isEditMode
        Logger.d("isEditMode: $isEditMode")
        if (isEditMode) {
            update(monthlyBudget)
        } else {
            add(monthlyBudget)
        }
    }

    private fun add(monthlyBudget: MonthlyBudgetParam) = viewModelScope.launch {
        monthlyBudgetRepository.add(monthlyBudget).fold(
            onSuccess = {
                emit(MonthlyBudgetEffect.NavigateBack)
            },
            onFailure = {
                Logger.e(messageString = it.message.toString())
            }
        )
    }

    private fun update(monthlyBudget: MonthlyBudgetParam) = viewModelScope.launch {
        monthlyBudgetRepository.update(monthlyBudget).fold(
            onSuccess = {
                emit(MonthlyBudgetEffect.NavigateBack)
            },
            onFailure = {
                Logger.e(messageString = it.message.toString())
            }
        )
    }

    private fun emit(effect: MonthlyBudgetEffect) = viewModelScope.launch {
        _effect.emit(effect)
    }
}