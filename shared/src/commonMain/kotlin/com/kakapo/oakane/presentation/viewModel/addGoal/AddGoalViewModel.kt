package com.kakapo.oakane.presentation.viewModel.addGoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.common.asCustomResult
import com.kakapo.common.subscribe
import com.kakapo.data.repository.base.GoalRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.domain.usecase.base.SaveGoalUseCase
import com.kakapo.model.Currency
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("AddGoalViewModelKt")
class AddGoalViewModel(
    private val goalRepository: GoalRepository,
    private val systemRepository: SystemRepository,
    private val saveGoalUseCase: SaveGoalUseCase
): ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(AddGoalState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<AddGoalEffect>()

    fun initData(goalId: Long){
        _uiState.update { it.copy(id = goalId) }
        if (goalId != 0L) {
            loadGoalBy(goalId)
        }
        loadCurrency()
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

    private fun loadCurrency() = viewModelScope.launch {
        val onSuccess: (Currency) -> Unit = { currency ->
            _uiState.update { it.copy(currency = currency) }
        }

        systemRepository.loadSavedCurrency().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun saveGoal() = viewModelScope.launch {
        val goal = uiState.value.asGoalModel()
        saveGoalUseCase.execute(goal).fold(
            onSuccess ={ emit(AddGoalEffect.SuccessSaveGoal) },
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