package com.kakapo.oakane.presentation.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.common.asCustomResult
import com.kakapo.common.subscribe
import com.kakapo.data.repository.base.GoalRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.data.repository.base.TransactionRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.domain.usecase.base.GetMonthlyBudgetOverviewUseCase
import com.kakapo.model.goal.GoalModel
import com.kakapo.model.monthlyBudget.MonthlyBudgetOverView
import com.kakapo.model.transaction.TransactionModel
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

@ObjCName("HomeViewModelKt")
class HomeViewModel(
    private val transactionRepository: TransactionRepository,
    private val goalRepository: GoalRepository,
    private val walletRepository: WalletRepository,
    private val monthlyBudgetOverviewUseCase: GetMonthlyBudgetOverviewUseCase,
    private val systemRepository: SystemRepository
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(HomeState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<HomeEffect>()

    fun initializeData() {
        loadRecentTransactions()
        loadGoals()
        loadWalletBalance()
        loadBudgetOverView()
        loadIsBalanceVisible()
    }

    fun handleEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.ToGoalWith -> emit(HomeEffect.ToGoalWith(event.id))
            is HomeEvent.ToTransactionWith -> emit(HomeEffect.ToTransaction(event.id))
            HomeEvent.ToCreateTransaction -> emit(HomeEffect.ToCreateTransaction)
            HomeEvent.ToTransactions -> emit(HomeEffect.ToTransactions)
            HomeEvent.OpenDrawer -> emit(HomeEffect.OpenDrawer)
            HomeEvent.ToCreateGoal -> emit(HomeEffect.ToCreateGoal)
            HomeEvent.ToGoals -> emit(HomeEffect.ToGoals)
            HomeEvent.ToMonthlyBudget -> emit(HomeEffect.ToMonthlyBudget)
            HomeEvent.ToWallets -> emit(HomeEffect.ToWallets)
            HomeEvent.ChangeBalanceVisibility -> changeBalanceVisibility()
        }
    }

    private fun loadRecentTransactions() = viewModelScope.launch {
        val onSuccess: (List<TransactionModel>) -> Unit = { transactions ->
            _uiState.update { it.copy(transactions = transactions.take(3)) }
        }
        transactionRepository.loadTransactions().asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun loadGoals() = viewModelScope.launch {
        val onSuccess: (List<GoalModel>) -> Unit = { goals ->
            _uiState.update { it.copy(goals = goals) }
        }
        goalRepository.loadGoals().asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun loadWalletBalance() = viewModelScope.launch {
        val onSuccess: (WalletModel) -> Unit = { wallet ->
            _uiState.update { it.copy(wallet = wallet) }
        }
        walletRepository.loadSelectedWallet().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun loadBudgetOverView() = viewModelScope.launch {
        val onSuccess: (MonthlyBudgetOverView) -> Unit = { monthlyBudget ->
            _uiState.update { it.copy(monthlyBudgetOverView = monthlyBudget) }
        }
        monthlyBudgetOverviewUseCase.execute().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun loadIsBalanceVisible() = viewModelScope.launch {
        val onSuccess: (Boolean) -> Unit = { isBalanceVisible ->
            _uiState.update { it.copy(isBalanceVisible = isBalanceVisible) }
        }
        systemRepository.isBalanceVisible().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun changeBalanceVisibility() = viewModelScope.launch {
        val currentVisibility = _uiState.value.isBalanceVisible
        val onSuccess: (Boolean) -> Unit = { isBalanceVisible ->
            _uiState.update { it.copy(isBalanceVisible = isBalanceVisible) }
        }
        systemRepository.changeBalance(!currentVisibility).fold(
            onSuccess =onSuccess,
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?){
        val message = throwable?.message ?: "Error"
        emit(HomeEffect.ShowError(message))
    }

    private fun emit(effect: HomeEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}