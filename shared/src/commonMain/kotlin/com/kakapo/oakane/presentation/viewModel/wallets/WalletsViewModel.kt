package com.kakapo.oakane.presentation.viewModel.wallets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.common.asCustomResult
import com.kakapo.common.subscribe
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.domain.usecase.selectedWalletUseCase
import com.kakapo.model.Currency
import com.kakapo.model.system.Theme
import com.kakapo.model.wallet.WalletItemModel
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

@ObjCName("WalletsViewModelKt")
class WalletsViewModel(
    private val walletRepository: WalletRepository,
    private val systemRepository: SystemRepository
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(WalletsState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<WalletsEffect>()

    fun initializeData(){
        loadWallets()
        loadCurrency()
        loadTheme()
    }

    fun handleEvent(event: WalletsEvent) {
        when (event) {
            WalletsEvent.NavigateBack -> emit(WalletsEffect.NavigateBack)
            is WalletsEvent.OnSearchBy -> _uiState.update { it.copy(searchQuery = event.query) }
            is WalletsEvent.ShowSheet -> showSheet(event.shown)
            is WalletsEvent.SelectPrimaryWalletBy -> selectWalletBy(event.id)
            is WalletsEvent.ClickedWallet -> emit(WalletsEffect.NavigateToWallet(event.item.id))
            is WalletsEvent.SaveWallet -> add(event.wallet)
        }
    }

    private fun loadWallets() = viewModelScope.launch {
        val onSuccess: (List<WalletItemModel>) -> Unit = { wallets ->
            _uiState.update { it.copy(wallets = wallets) }
        }
        walletRepository.loadWalletItems().asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun loadCurrency() = viewModelScope.launch {
        val onSuccess: (Currency) -> Unit = { currency ->
            _uiState.update { it.copy(currency = currency) }
        }
        systemRepository.loadSavedCurrency().fold(
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

    private fun add(wallet: WalletModel) = viewModelScope.launch {
        walletRepository.save(wallet).fold(
            onSuccess = { loadWallets() },
            onFailure = ::handleError
        )
    }

    private fun selectWalletBy(id: Long) = viewModelScope.launch {
        val wallets = uiState.value.wallets.selectedWalletUseCase(id)
        walletRepository.saveWallet(id).fold(
            onSuccess = { _uiState.update { it.copy(wallets = wallets) } },
            onFailure = ::handleError
        )
    }

    private fun showSheet(shown: Boolean) = viewModelScope.launch {
        _uiState.update { it.copy(isSheetShown = shown) }
        if (!shown) {
            emit(WalletsEffect.DismissBottomSheet)
        }
    }

    private fun handleError(throwable: Throwable?) {
        val message = throwable?.message ?: "Unknown error"
        emit(WalletsEffect.ShowError(message))
    }

    private fun emit(effect: WalletsEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}