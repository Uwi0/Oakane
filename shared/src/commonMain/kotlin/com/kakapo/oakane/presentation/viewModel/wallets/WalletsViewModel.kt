package com.kakapo.oakane.presentation.viewModel.wallets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.oakane.presentation.model.WalletSheetContent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WalletsViewModel() : ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(WalletsState())

    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<WalletsEffect>()

    fun handleEvent(event: WalletsEvent) {
        when (event) {
            is WalletsEvent.NavigateBack -> emit(WalletsEffect.NavigateBack)
            is WalletsEvent.OnSearchBy -> _uiState.update { it.copy(searchQuery = event.query) }
            is WalletsEvent.IsSheet -> showSheet(event.shown)
            is WalletsEvent.SelectedSheet -> _uiState.update { it.copy(sheetContent = event.content) }
            is WalletsEvent.SelectWallet -> _uiState.update { it.selectedWallet(event.color) }
            is WalletsEvent.OnChangeWallet -> _uiState.update { it.copy(walletName = event.name) }
            is WalletsEvent.ChangeStart -> _uiState.update { it.copy(startBalance = event.balance) }
            is WalletsEvent.SelectedIcon -> _uiState.update { it.copy(selectedIcon = event.name) }
            is WalletsEvent.SelectedImage -> _uiState.update { it.updateImage(event.file) }
            WalletsEvent.FeatureNotAvailable -> emit(WalletsEffect.ShowError("Feature not available yet"))
            WalletsEvent.ConfirmIcon -> _uiState.update { it.copy(sheetContent = WalletSheetContent.Create) }
        }
    }

    private fun showSheet(shown: Boolean) {
        _uiState.update { it.copy(isSheetShown = shown) }
        if (!shown) {
            emit(WalletsEffect.DismissBottomSheet)
        }
    }

    private fun emit(effect: WalletsEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}