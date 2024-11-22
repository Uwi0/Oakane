package com.kakapo.oakane.presentation.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.repository.base.GoalRepository
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.GoalModel
import com.kakapo.oakane.model.transaction.TransactionModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val transactionRepository: TransactionRepository,
    private val goalRepository: GoalRepository
) : ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(HomeState())

    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<HomeEffect>()

    fun initializeData() {
        loadRecentTransactions()
        loadGoals()
    }

    fun handleEvent(event: HomeEvent) {
        when(event){
            HomeEvent.ToCreateTransaction -> emit(HomeEffect.ToCreateTransaction)
            HomeEvent.ToTransactions -> emit(HomeEffect.ToTransactions)
            HomeEvent.OpenDrawer -> emit(HomeEffect.OpenDrawer)
            HomeEvent.ToCreateGoal -> emit(HomeEffect.ToCreateGoal)
        }
    }

    private fun loadRecentTransactions() = viewModelScope.launch {
        val onSuccess: (List<TransactionModel>) -> Unit = { transactions ->
            _uiState.update { it.copy(transactions = transactions.take(3)) }
        }
        transactionRepository.loadTransactions().collect { result ->
            result.fold(
                onSuccess = onSuccess,
                onFailure = ::handleError
            )
        }
    }

    private fun loadGoals() = viewModelScope.launch {
        val onSuccess: (List<GoalModel>) -> Unit = { goals ->
            _uiState.update { it.copy(goals = goals) }
        }
        goalRepository.loadGoals().collect { result ->
            result.fold(
                onSuccess = onSuccess,
                onFailure = ::handleError
            )
        }
    }

    private fun handleError(throwable: Throwable?){
        val message = throwable?.message ?: "Error"
        emit(HomeEffect.ShowError(message))
        Logger.e { "Error HomeViewModel: $message" }
    }

    private fun emit(effect: HomeEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}