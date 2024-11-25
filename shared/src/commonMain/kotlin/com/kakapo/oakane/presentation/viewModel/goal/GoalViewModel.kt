package com.kakapo.oakane.presentation.viewModel.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.oakane.common.asDouble
import com.kakapo.oakane.data.repository.base.GoalRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GoalViewModel(
    private val repository: GoalRepository
): ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(GoalState())

    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<GoalEffect>()

    fun initializeData(goalId: Long) {
        loadGoalBy(goalId)
    }

    fun handleEvent(event: GoalEvent) {
        when(event) {
            is GoalEvent.Dialog -> _uiState.update { it.copy(dialogShown = event.shown) }
            is GoalEvent.Change -> _uiState.update { it.copy(savingAmount = event.amount) }
            GoalEvent.NavigateBack -> emit(GoalEffect.NavigateBack)
            GoalEvent.AddSaving -> addSaving()
        }
    }

    private fun loadGoalBy(id: Long) = viewModelScope.launch {
        repository.loadGoalBy(id).collect { result ->
            result.fold(
                onSuccess = { goal ->
                    _uiState.update { it.copy(goal = goal) }
                },
                onFailure = {}
            )
        }
    }

    private fun addSaving() = viewModelScope.launch {
        val id = uiState.value.goal.id
        val amount = uiState.value.savingAmount.asDouble()
        repository.addSaved(amount, id).fold(
            onSuccess = { updateGoal(amount) },
            onFailure = {}
        )
    }

    private fun updateGoal(amount: Double) {
        val currentAmount = uiState.value.goal.savedMoney
        val newAmount = currentAmount + amount
        _uiState.update { it.copy(goal = it.goal.copy(savedMoney = newAmount), dialogShown = false) }
    }

    private fun emit(effect: GoalEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}