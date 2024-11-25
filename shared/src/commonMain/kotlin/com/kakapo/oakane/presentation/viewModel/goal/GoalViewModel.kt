package com.kakapo.oakane.presentation.viewModel.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.oakane.data.repository.base.GoalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GoalViewModel(
    private val repository: GoalRepository
): ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(GoalState())

    fun initializeData(goalId: Long) {
        loadGoalBy(goalId)
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
}