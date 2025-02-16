package com.kakapo.oakane.presentation.viewModel.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.common.asCustomResult
import com.kakapo.common.subscribe
import com.kakapo.data.repository.base.GoalRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.model.goal.GoalModel
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

@ObjCName("GoalsViewModelKt")
class GoalsViewModel(
    private val goalRepository: GoalRepository,
    private val systemRepository: SystemRepository
): ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(GoalsState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<GoalsEffect>()

    fun initData() {
        loadGoals()
        loadTheme()
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
        val onSuccess: (List<GoalModel>) -> Unit = { goals ->
            _uiState.update { it.copy(goals = goals, filteredGoals = goals) }
        }
        goalRepository.loadGoals().asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
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

    private fun filterGoalsBy(query: String) {
        val goals = _uiState.value.goals
        val filteredGoals = goals.filter { it.name.contains(query, ignoreCase = true) }
        _uiState.update { it.copy(filteredGoals = filteredGoals) }
    }

    private fun handleError(throwable: Throwable?) {
        emit(GoalsEffect.ShowError(throwable?.message ?: "An error occurred"))
    }

    private fun emit(effect: GoalsEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}