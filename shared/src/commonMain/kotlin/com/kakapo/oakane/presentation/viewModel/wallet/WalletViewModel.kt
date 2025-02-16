package com.kakapo.oakane.presentation.viewModel.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.common.asCustomResult
import com.kakapo.common.subscribe
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.domain.usecase.base.MoveWalletBalanceUseCase
import com.kakapo.domain.usecase.base.WalletLogItemsUseCase
import com.kakapo.domain.usecase.toExpenseAndIncome
import com.kakapo.model.system.Theme
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.model.wallet.WalletLogItem
import com.kakapo.model.wallet.WalletModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WalletViewModel(
    private val walletRepository: WalletRepository,
    private val systemRepository: SystemRepository,
    private val moveBalanceUseCase: MoveWalletBalanceUseCase,
    private val walletLogItemsUseCase: WalletLogItemsUseCase
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(WalletState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect
    private val _uiEffect = MutableSharedFlow<WalletEffect>()

    fun initData(walletId: Long) {
        loadWalletTransactionsLogs(walletId)
        loadWallets()
        loadTheme()
    }

    fun handleEvent(event: WalletEvent) {
        when (event) {
            WalletEvent.NavigateBack -> emit(WalletEffect.NavigateBack)
            WalletEvent.MoveBalance -> moveBalance()
            WalletEvent.ConfirmDelete -> deleteWallet()
            is WalletEvent.ShowDialog -> _uiState.update { it.showDialog(event) }
            is WalletEvent.AddNote -> _uiState.update { it.copy(moveBalanceNote = event.note) }
            is WalletEvent.AddSelectedWalletTo -> _uiState.update { it.copy(selectedWalletTo = event.wallet) }
            is WalletEvent.AddBalance -> _uiState.update { it.copy(movedBalance = event.balance) }
            is WalletEvent.SearchLog -> _uiState.update { it.copy(searchQuery = event.query) }
            is WalletEvent.ShowWalletSheet -> onWalletSheet(event.shown)
            is WalletEvent.UpdateWallet -> update(event.wallet)
            is WalletEvent.ShowFilterSheet -> onFilterSheet(event.shown)
            is WalletEvent.FilterLog -> applyFilterLog(event)
            WalletEvent.ResetFilterLog -> _uiState.update { it.resetFilter() }
        }
    }

    private fun loadWallets() = viewModelScope.launch {
        val onSuccess: (List<WalletModel>) -> Unit = { wallets ->
            _uiState.value = _uiState.value.copy(wallets = wallets)
        }
        walletRepository.loadWallets().asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun loadWalletTransactionsLogs(walletId: Long) = viewModelScope.launch {
        val onSuccess: (List<WalletLogItem<*>>) -> Unit  = { logs ->
            _uiState.update { it.copy(logItems = logs) }
            loadWalletBy(walletId, logs)
        }
        walletLogItemsUseCase.execute(walletId).asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun loadWalletBy(id: Long, logs: List<WalletLogItem<*>>) = viewModelScope.launch {
        val (expense, income) = logs.toExpenseAndIncome()
        val onSuccess: (WalletItemModel) -> Unit = { wallet ->
            _uiState.update { it.copy(wallet = wallet.copy(expense = expense, income = income)) }
        }
        walletRepository.loadWalletItemBy(id).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun loadTheme() = viewModelScope.launch {
        val onSuccess: (Theme) -> Unit = { theme ->
            _uiState.update { it.copy(theme = theme) }
        }
        systemRepository.loadSavedTheme().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun moveBalance() = viewModelScope.launch {
        val moveBalance = _uiState.value.asWalletTransfer()
        val onSuccess: (Unit) -> Unit =  {
            _uiState.update { it.copy(dialogVisible = false) }
        }
        moveBalanceUseCase.execute(moveBalance).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun update(wallet: WalletModel) = viewModelScope.launch {
        val logs = _uiState.value.logItems
        val onSuccess: (Unit) -> Unit = {
            _uiState.update { it.copy(isWalletSheetShown = false) }
            emit(WalletEffect.DismissWalletSheet)
            loadWalletBy(wallet.id, logs)
        }
        walletRepository.update(wallet).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun deleteWallet() = viewModelScope.launch {
        val walletId = _uiState.value.wallet.id
        val onSuccess: (Unit) -> Unit = {
            _uiState.update { it.copy(dialogVisible = false) }
            emit(WalletEffect.ShowError("What is Done cannot be Undone"))
            emit(WalletEffect.NavigateBack)
        }
        walletRepository.deleteWalletBy(walletId).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun onWalletSheet(shown: Boolean) {
        _uiState.update { it.copy(isWalletSheetShown = shown) }
        if (!shown) {
            emit(WalletEffect.DismissWalletSheet)
        }
    }

    private fun onFilterSheet(shown: Boolean) {
        _uiState.update { it.copy(isFilterSheetShown = shown) }
        if (!shown) {
            emit(WalletEffect.DismissFilterSheet)
        }
    }

    private fun applyFilterLog(event: WalletEvent.FilterLog) {
        _uiState.update { it.applyFilter(event) }
        emit(WalletEffect.DismissFilterSheet)
    }

    private fun handleError(throwable: Throwable?) {
        emit(WalletEffect.ShowError(throwable?.message ?: "Unknown error"))
    }

    private fun emit(effect: WalletEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}