package com.kakapo.oakane.presentation.viewModel.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.common.asCustomResult
import com.kakapo.common.subscribe
import com.kakapo.data.repository.base.GoalRepository
import com.kakapo.data.repository.base.GoalSavingsRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.domain.usecase.base.AddGoalSavingUseCase
import com.kakapo.model.Currency
import com.kakapo.model.goal.GoalSavingModel
import com.kakapo.model.wallet.WalletModel
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
    private val goalRepository: GoalRepository,
    private val goalSavingRepository: GoalSavingsRepository,
    private val systemRepository: SystemRepository,
    private val walletRepository: WalletRepository,
    private val addGoalSavingUseCase: AddGoalSavingUseCase
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(GoalState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<GoalEffect>()

    fun initializeData(goalId: Long) {
        loadGoalBy(goalId).invokeOnCompletion {
            loadGoalSavingsBy(goalId)
        }
        loadCurrency()
        loadWallets()
        loadSelectedWallet()
    }

    fun handleEvent(event: GoalEvent) {
        when (event) {
            is GoalEvent.Dialog -> _uiState.update { it.updateDialog(event.shown, event.content) }
            is GoalEvent.Change -> _uiState.update { it.copy(savingAmount = event.amount) }
            GoalEvent.NavigateBack -> emit(GoalEffect.NavigateBack)
            GoalEvent.AddSaving -> addSaving()
            GoalEvent.DeleteGoal -> deleteGoal()
            GoalEvent.UpdateGoal -> updateGoal()
            is GoalEvent.AddNote -> _uiState.update { it.copy(note = event.note) }
            is GoalEvent.ChangeWallet -> _uiState.update { it.copy(selectedWallet = event.wallet) }
        }
    }

    private fun loadGoalBy(id: Long) = viewModelScope.launch {
        goalRepository.loadGoalBy(id).asCustomResult().subscribe(
            onSuccess = { goal -> _uiState.update { it.copy(goal = goal) } },
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

    private fun loadWallets() = viewModelScope.launch {
        val onSuccess: (List<WalletModel>) -> Unit = { wallets ->
            _uiState.update { it.copy(wallets = wallets) }
        }
        walletRepository.loadWallets().asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun loadSelectedWallet() = viewModelScope.launch {
        val onSuccess: (WalletModel) -> Unit = { wallet ->
            _uiState.update { it.copy(selectedWallet = wallet) }
        }
        walletRepository.loadSelectedWallet().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun loadGoalSavingsBy(id: Long) = viewModelScope.launch {
        val onSuccess: (List<GoalSavingModel>) -> Unit = { savings ->
            _uiState.update { it.copy(goalSavings = savings) }
        }
        goalSavingRepository.loadGoalSavingBy(id).asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun addSaving() = viewModelScope.launch {
        val param = uiState.value.goalSavingParam()
        val onSuccess: (Unit) -> Unit = {
            updateGoal(param.amount)
            loadGoalSavingsBy(param.goalId)
        }

        addGoalSavingUseCase.execute(param).fold(
            onSuccess = onSuccess,
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
        goalRepository.deleteGoalBy(id).fold(
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