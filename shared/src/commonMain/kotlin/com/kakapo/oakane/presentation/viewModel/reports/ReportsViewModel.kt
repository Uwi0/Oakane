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
import com.kakapo.oakane.model.wallet.WalletItemModel
import com.kakapo.oakane.presentation.viewModel.reports.model.MonthReport
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
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
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(ReportsState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect
    private val _uiEffect = MutableSharedFlow<ReportsEffect>()

    fun initializeData() {
        loadMonthlyBudgetOverView()
        loadTransactionCategories()
        loadTotalBalance()
        loadWallets()
    }

    fun handleEVent(event: ReportsEvent) {
        when (event) {
            is ReportsEvent.NavigateBack -> emit(ReportsEffect.NavigateBack)
            is ReportsEvent.Selected -> onSelected(event.wallet)
            is ReportsEvent.FilterBy -> onFilterByMonth(event.month)
        }
    }

    private fun onSelected(wallet: WalletItemModel) {
        if (wallet.isDefaultWallet()) loadDefaultValue()
        else loadTransactionCategoriesWith(wallet)
    }

    private fun loadMonthlyBudgetOverView(walletId: Long? = null) = viewModelScope.launch {
        val (startDateOfMont, endDateOfMonth) = _uiState.value.monthNumber
        val onSuccess: (MonthlyBudgetOverViewModel) -> Unit = { monthlyOverView ->
            _uiState.update { it.copy(monthlyOverView = monthlyOverView) }
        }
        getMonthlyBudgetOverview.execute(walletId, startDateOfMont, endDateOfMonth).fold(
            onSuccess = onSuccess,
            onFailure = {}
        )
    }

    private fun loadTransactionCategories() = viewModelScope.launch {
        val (startDateOfMont, endDateOfMonth) = _uiState.value.monthNumber
        val onSuccess: (List<ReportModel>) -> Unit = { reports ->
            _uiState.update { it.copy(reports = reports) }
        }

        transactionRepository.loadTransactionsCategories(
            startDateOfMont,
            endDateOfMonth
        ).asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun loadTransactionCategoriesWith(walletItem: WalletItemModel) = viewModelScope.launch {
        val (startDateOfMont, endDateOfMonth) = _uiState.value.monthNumber
        val onSuccess: (List<ReportModel>) -> Unit = { reports ->
            _uiState.update { it.updateSelected(walletItem, reports) }
            loadMonthlyBudgetOverView(walletItem.id)
        }

        transactionRepository.loadTransactionsCategoriesBy(
            walletItem.id,
            startDateOfMont,
            endDateOfMonth
        ).asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun loadTotalBalance() = viewModelScope.launch {
        val onSuccess: (Double) -> Unit = { totalBalance ->
            _uiState.update { it.updateBalance(totalBalance) }
        }

        walletRepository.loadTotalBalance().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun loadWallets() = viewModelScope.launch {
        val onSuccess: (List<WalletItemModel>) -> Unit = { wallets ->
            _uiState.update { it.copy(wallets = wallets) }
        }

        walletRepository.loadWallets().asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun loadDefaultValue() {
        initializeData()
        _uiState.update { it.copy(selectedWallet = null, selectedWalletName = "All Wallet") }
    }

    private fun onFilterByMonth(month: MonthReport) {
        val wallet = _uiState.value.selectedWallet
        _uiState.update { it.copy(selectedMonth = month) }
        if (wallet != null) loadTransactionCategoriesWith(wallet)
        else loadDefaultValue()
    }

    private fun handleError(throwable: Throwable?) {
        val message = throwable?.message ?: "Unknown error"
        emit(ReportsEffect.ShowError(message))
    }

    private fun emit(effect: ReportsEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}