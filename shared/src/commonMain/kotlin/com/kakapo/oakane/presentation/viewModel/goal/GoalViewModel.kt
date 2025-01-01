package com.kakapo.oakane.presentation.viewModel.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.common.asDouble
import com.kakapo.oakane.data.repository.base.GoalRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("GoalViewModelKt")
class GoalViewModel(
    private val repository: GoalRepository
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(GoalState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<GoalEffect>()

    fun initializeData(goalId: Long) {
        loadGoalBy(goalId)
    }

    fun handleEvent(event: GoalEvent) {
        when (event) {
            is GoalEvent.Dialog -> _uiState.update { it.updateDialog(event.shown, event.content) }
            is GoalEvent.Change -> _uiState.update { it.copy(savingAmount = event.amount) }
            GoalEvent.NavigateBack -> emit(GoalEffect.NavigateBack)
            GoalEvent.AddSaving -> addSaving()
            GoalEvent.DeleteGoal -> deleteGoal()
            GoalEvent.UpdateGoal -> updateGoal()
        }
    }

    private fun loadGoalBy(id: Long) = viewModelScope.launch {
        repository.loadGoalBy(id).collect { result ->
            result.fold(
                onSuccess = { goal -> _uiState.update { it.copy(goal = goal) } },
                onFailure = ::handleError
            )
        }
    }

    private fun addSaving() = viewModelScope.launch {
        val id = uiState.value.goal.id
        val amount = uiState.value.savingAmount.asDouble()
        repository.addSaved(amount, id).fold(
            onSuccess = { updateGoal(amount) },
            onFailure = ::handleError
        )
    }

    private fun updateGoal(amount: Double) {
        val currentAmount = uiState.value.goal.savedMoney
        val newAmount = currentAmount + amount
        _uiState.update {
            it.copy(
                goal = it.goal.copy(savedMoney = newAmount),
                dialogShown = false
            )
        }
    }

    private fun deleteGoal() = viewModelScope.launch {
        val id = uiState.value.goal.id
        repository.deleteGoalBy(id).fold(
            onSuccess = {
                _uiState.update { it.copy(dialogShown = false) }
                emit(GoalEffect.NavigateBack)
            },
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        val message = throwable?.message ?: "Something went wrong"
        emit(GoalEffect.ShowError(message))
    }

    private fun updateGoal() {
        val goalId = uiState.value.goal.id
        emit(GoalEffect.UpdateGoalBy(goalId))
    }

    private fun emit(effect: GoalEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}