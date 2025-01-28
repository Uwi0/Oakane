package com.kakapo.oakane.presentation.viewModel.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.model.wallet.WalletItemModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WalletViewModel(
    private val walletRepository: WalletRepository
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(WalletState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect
    private val _uiEffect = MutableSharedFlow<WalletEffect>()

    fun initData(walletId: Long) {
        loadWalletBy(walletId)
    }

    fun handleEvent(event: WalletEvent) {
        when (event) {
            WalletEvent.NavigateBack -> emit(WalletEffect.NavigateBack)
            WalletEvent.EditWallet -> {}
            WalletEvent.DeleteWallet -> {}
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

    private fun handleError(throwable: Throwable?) {
        emit(WalletEffect.ShowError(throwable?.message ?: "Unknown error"))
    }

    private fun emit(effect: WalletEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}