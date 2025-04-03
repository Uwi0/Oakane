package com.kakapo.oakane.presentation.viewModel.createWallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.model.Currency
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.model.wallet.WalletModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("CreateWalletViewModelKt")
class CreateWalletViewModel(
    private val systemRepository: SystemRepository,
    private val walletRepository: WalletRepository
): ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(CreateWalletState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect
    private val _uiEffect = MutableSharedFlow<CreateWalletEffect>()

    fun initData(walletId: Long) {
        loadCurrency()
        if (walletId != 0L) {
            _uiState.update { it.copy(id = walletId) }
            loadWalletItemBy(walletId)
        }
    }

    fun handleEvent(event: CreateWalletEvent) {
        when(event) {
            CreateWalletEvent.NavigateBack -> emit(CreateWalletEffect.NavigateBack)
            CreateWalletEvent.SaveWallet -> saveWallet()
            is CreateWalletEvent.WalletNameChanged -> _uiState.update { it.copy(walletName = event.name) }
            is CreateWalletEvent.NoteChanged -> _uiState.update { it.copy(note = event.note) }
            is CreateWalletEvent.BalanceChanged -> _uiState.update { it.copy(balance = event.balance) }
            is CreateWalletEvent.ShowSheet -> _uiState.update { it.updateShowSheet(event) }
            is CreateWalletEvent.SelectedColor -> _uiState.update { it.updateSelectedColor(event) }
            is CreateWalletEvent.SelectedIcon -> _uiState.update { it.updateSelectedIcon(event) }
            is CreateWalletEvent.SelectedImage -> _uiState.update { it.updateSelectedImage(event) }
        }
    }

    private fun loadCurrency() = viewModelScope.launch {
        val onSuccess: (Currency) -> Unit = {
            _uiState.update { it.copy(currency = it.currency) }
        }

        systemRepository.loadSavedCurrency().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun loadWalletItemBy(id: Long) = viewModelScope.launch {
        val onSuccess: (WalletItemModel) -> Unit = { wallet ->
            _uiState.update { it.update(wallet) }
        }

        walletRepository.loadWalletItemBy(id).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun saveWallet() = viewModelScope.launch {
        val walletModel = _uiState.value.asWalletModel()
        if (walletModel.id == 0L) {
            add(walletModel)
        } else {
            update(walletModel)
        }
    }

    private suspend fun add(wallet: WalletModel) {
        val onSuccess: (Unit) -> Unit = {
            emit(CreateWalletEffect.NavigateBack)
        }
        walletRepository.save(wallet).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private suspend fun update(wallet: WalletModel) {
        val onSuccess: (Unit) -> Unit = {
            emit(CreateWalletEffect.NavigateBack)
        }
        walletRepository.update(wallet).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        emit(CreateWalletEffect.ShowError(throwable?.message ?: "Unknown error"))
    }

    private fun emit(effect: CreateWalletEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}