package com.kakapo.oakane.presentation.viewModel.createWallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("CreateWalletViewModelKt")
class CreateWalletViewModel: ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(CreateWalletState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect
    private val _uiEffect = MutableSharedFlow<CreateWalletEffect>()

    fun handleEvent(event: CreateWalletEvent) {
        when(event) {
            is CreateWalletEvent.WalletNameChanged -> _uiState.update { it.copy(walletName = event.name) }
            CreateWalletEvent.NavigateBack -> emit(CreateWalletEffect.NavigateBack)
            CreateWalletEvent.CreateWallet -> TODO()
        }
    }

    private fun emit(effect: CreateWalletEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}