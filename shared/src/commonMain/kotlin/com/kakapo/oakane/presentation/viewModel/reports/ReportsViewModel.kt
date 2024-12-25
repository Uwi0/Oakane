package com.kakapo.oakane.presentation.viewModel.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.oakane.common.asCustomResult
import com.kakapo.oakane.common.subscribe
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.data.repository.base.WalletRepository
import com.kakapo.oakane.domain.usecase.base.GetMonthlyBudgetOverviewUseCase
import com.kakapo.oakane.model.ReportModel
import com.kakapo.oakane.model.monthlyBudget.MonthlyBudgetOverViewModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("ReportsViewModelKt")
class ReportsViewModel(
    private val getMonthlyBudgetOverview: GetMonthlyBudgetOverviewUseCase,
    private val transactionRepository: TransactionRepository,
    private val walletRepository: WalletRepository
): ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(ReportsState())

    fun initializeData(){
        loadMonthlyBudgetOverView()
        loadTransactionCategories()
        loadTotalBalance()
    }

    private fun loadMonthlyBudgetOverView() = viewModelScope.launch {
        val onSuccess: (MonthlyBudgetOverViewModel) -> Unit = { monthlyOverView ->
            _uiState.update { it.copy(monthlyOverView = monthlyOverView) }
        }
        getMonthlyBudgetOverview.execute().fold(
            onSuccess = onSuccess,
            onFailure = {}
        )
    }

    private fun loadTransactionCategories() = viewModelScope.launch {
        val onSuccess: (List<ReportModel>) -> Unit = { reports ->
            _uiState.update { it.copy(reports = reports) }
        }

        transactionRepository.loadTransactionsCategories().asCustomResult().subscribe(
            onSuccess = onSuccess,
        )
    }

    private fun loadTotalBalance() = viewModelScope.launch {
        val onSuccess: (Double) -> Unit = { totalBalance ->
            _uiState.update { it.copy(totalBalance = totalBalance) }
        }

        walletRepository.loadTotalBalance().fold(
            onSuccess = onSuccess,
            onFailure = {}
        )
    }
}