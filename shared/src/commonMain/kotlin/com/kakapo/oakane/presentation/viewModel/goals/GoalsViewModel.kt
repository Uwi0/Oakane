package com.kakapo.oakane.presentation.viewModel.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.data.repository.base.GoalRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("GoalsViewModelKt")
class GoalsViewModel(
    private val repository: GoalRepository
): ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(GoalsState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<GoalsEffect>()

    fun initData() {
        loadGoals()
    }

    fun handleEvent(event: GoalsEvent) {
        when(event) {
            is GoalsEvent.FilterBy -> filterGoalsBy(event.query)
            is GoalsEvent.NavigateToGoal -> emit(GoalsEffect.NavigateToGoal(event.id))
            GoalsEvent.NavigateBack -> emit(GoalsEffect.NavigateBack)
            GoalsEvent.AddGoal -> emit(GoalsEffect.AddGoal)
        }
    }

    private fun loadGoals() = viewModelScope.launch {
        repository.loadGoals().collect{ result ->
            result.fold(
                onSuccess = { goals ->
                    _uiState.update { it.copy(goals = goals, filteredGoals = goals) }
                },
                onFailure = {
                    Logger.e(it) { "Error loading goals" }
                }
            )
        }
    }

    private fun filterGoalsBy(query: String) {
        val goals = _uiState.value.goals
        val filteredGoals = goals.filter { it.goalName.contains(query, ignoreCase = true) }
        _uiState.update { it.copy(filteredGoals = filteredGoals) }
    }

    private fun emit(effect: GoalsEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}