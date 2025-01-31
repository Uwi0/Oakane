package com.kakapo.oakane.presentation.viewModel.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.common.asCustomResult
import com.kakapo.common.subscribe
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.domain.usecase.base.MoveWalletBalanceUseCase
import com.kakapo.domain.usecase.base.WalletLogItemsUseCase
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
        loadWalletBy(walletId)
        loadWallets()
        loadWalletTransactionsLogs(walletId)
    }

    fun handleEvent(event: WalletEvent) {
        when (event) {
            WalletEvent.NavigateBack -> emit(WalletEffect.NavigateBack)
            WalletEvent.EditWallet -> {}
            WalletEvent.DeleteWallet -> {}
            WalletEvent.MoveBalance -> moveBalance()
            is WalletEvent.DialogShown -> _uiState.update { it.copy(dialogVisible = event.shown) }
            is WalletEvent.AddNote -> _uiState.update { it.copy(moveBalanceNote = event.note) }
            is WalletEvent.AddSelectedWalletFrom -> _uiState.update { it.copy(selectedWalletFrom = event.wallet) }
            is WalletEvent.AddSelectedWalletTo -> _uiState.update { it.copy(selectedWalletTo = event.wallet) }
            is WalletEvent.AddBalance -> _uiState.update { it.copy(movedBalance = event.balance) }
            is WalletEvent.SearchLog -> _uiState.update { it.copy(searchQuery = event.query) }
        }
    }

    private fun loadWalletBy(id: Long) = viewModelScope.launch {
        val onSuccess: (WalletItemModel) -> Unit = { wallet ->
            _uiState.value = _uiState.value.copy(wallet = wallet)
        }
        walletRepository.loadWalletItemBy(id).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
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
            Logger.d("Wallet logs: $logs")
            _uiState.update { it.copy(logItems = logs) }
        }
        walletLogItemsUseCase.execute(walletId).asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
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

    private fun handleError(throwable: Throwable?) {
        emit(WalletEffect.ShowError(throwable?.message ?: "Unknown error"))
    }

    private fun emit(effect: WalletEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}