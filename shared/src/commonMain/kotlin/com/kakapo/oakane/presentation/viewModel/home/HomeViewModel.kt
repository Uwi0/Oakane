package com.kakapo.oakane.presentation.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.model.transaction.TransactionModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(HomeState())

    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<HomeEffect>()

    fun initializeData() {
        loadRecentTransactions()
    }

    fun handleEvent(event: HomeEvent) {
        when(event){
            HomeEvent.ToCreateTransaction -> emit(HomeEffect.ToCreateTransaction)
            HomeEvent.ToTransactions -> emit(HomeEffect.ToCreateTransaction)
            HomeEvent.OpenDrawer -> emit(HomeEffect.OpenDrawer)
        }
    }

    private fun loadRecentTransactions() = viewModelScope.launch {
        val onSuccess: (List<TransactionModel>) -> Unit = { transactions ->
            _uiState.update { it.copy(transactions = transactions) }
        }
        repository.loadRecentTransactions().collect { result ->
            result.fold(
                onSuccess = onSuccess,
                onFailure = { e ->
                    Logger.e(messageString = "error load recent transaction ${e.message}")
                }
            )
        }
    }

    private fun emit(effect: HomeEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}