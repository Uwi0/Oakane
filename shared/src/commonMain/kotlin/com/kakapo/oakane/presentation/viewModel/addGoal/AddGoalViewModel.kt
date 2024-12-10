package com.kakapo.oakane.presentation.viewModel.addGoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.oakane.common.asCustomResult
import com.kakapo.oakane.common.subscribe
import com.kakapo.oakane.data.repository.base.GoalRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddGoalViewModel(
    private val goalRepository: GoalRepository
): ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(AddGoalState())

    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<AddGoalEffect>()

    fun initData(goalId: Long){
        _uiState.update { it.copy(id = goalId) }
        if (goalId != 0L) loadGoalBy(goalId)
    }

    fun handleEvent(event: AddGoalEvent){
        when(event){
            is AddGoalEvent.SetEnd -> _uiState.update { it.updateEnd(event.date) }
            is AddGoalEvent.SetName -> _uiState.update { it.copy(goalName = event.value) }
            is AddGoalEvent.SetNote -> _uiState.update { it.copy(note = event.value) }
            is AddGoalEvent.SetStart -> _uiState.update { it.updateStart(event.date) }
            is AddGoalEvent.SetTarget -> _uiState.update { it.copy(targetAmount = event.amount) }
            is AddGoalEvent.ShowDialog -> _uiState.update { it.updateDialog(event) }
            is AddGoalEvent.SetFile -> _uiState.update { it.copy(fileName = event.name) }
            AddGoalEvent.HideDialog -> _uiState.update { it.copy(dialogShown = false) }
            AddGoalEvent.NavigateBack -> emit(AddGoalEffect.NavigateBack)
            AddGoalEvent.SaveGoal -> saveGoal()
        }
    }

    private fun loadGoalBy(id: Long) = viewModelScope.launch {
        goalRepository.loadGoalBy(id).asCustomResult().subscribe(
            onSuccess = { goalResult -> _uiState.update { it.update(goalResult) } },
            onError = ::handleError
        )
    }

    private fun saveGoal() = viewModelScope.launch {
        val isEditMode = uiState.value.isEditMode
        if (isEditMode) updateGoal()
        else addGoal()
    }

    private fun addGoal() = viewModelScope.launch {
        val goal = uiState.value.asGoalModel()
        goalRepository.save(goal).fold(
            onSuccess = { emit(AddGoalEffect.SuccessSaveGoal) },
            onFailure = ::handleError
        )
    }

    private fun updateGoal() = viewModelScope.launch {
        val goal = uiState.value.asGoalModel()
        val id = uiState.value.id
        goalRepository.update(goal, id).fold(
            onSuccess = { emit(AddGoalEffect.SuccessSaveGoal) },
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        emit(AddGoalEffect.ShowError(throwable?.message ?: "Error"))
    }

    private fun emit(effect: AddGoalEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}