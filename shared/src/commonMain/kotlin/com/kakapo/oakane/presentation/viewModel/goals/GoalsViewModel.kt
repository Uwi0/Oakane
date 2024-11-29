package com.kakapo.oakane.presentation.viewModel.goals

import androidx.lifecycle.ViewModel
import com.kakapo.oakane.data.repository.base.GoalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GoalsViewModel(
    private val repository: GoalRepository
): ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(GoalsState())

    fun handleEvent(event: GoalsEvent) {
        
    }
}