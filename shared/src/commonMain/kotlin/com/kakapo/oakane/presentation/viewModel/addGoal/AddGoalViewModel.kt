package com.kakapo.oakane.presentation.viewModel.addGoal

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddGoalViewModel: ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(AddGoalState())

    fun handleEvent(event: AddGoalEvent){
        when(event){
            is AddGoalEvent.SetEnd -> _uiState.update { it.updateEnd(event.date) }
            is AddGoalEvent.SetName -> _uiState.update { it.copy(goalName = event.value) }
            is AddGoalEvent.SetNote -> _uiState.update { it.copy(note = event.value) }
            is AddGoalEvent.SetStart -> _uiState.update { it.updateStart(event.date) }
            is AddGoalEvent.SetTarget -> _uiState.update { it.copy(targetAmount = event.amount) }
            AddGoalEvent.SaveGoal -> {}
            is AddGoalEvent.ShowDialog -> _uiState.update { it.updateDialog(event) }
            AddGoalEvent.HideDialog -> _uiState.update { it.copy(dialogShown = false) }
        }
    }
}