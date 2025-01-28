package com.kakapo.oakane.presentation.viewModel.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.WalletRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WalletViewModel(
    private val walletRepository: WalletRepository
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(WalletState())

    fun initData(walletId: Long) {
        loadWalletBy(walletId)
    }

    private fun loadWalletBy(id: Long) = viewModelScope.launch {
        walletRepository.loadWalletItemBy(id).fold(
            onSuccess = { wallet ->
                _uiState.value = _uiState.value.copy(wallet = wallet)
            },
            onFailure = { error ->
                // handle error
            }
        )
    }
}