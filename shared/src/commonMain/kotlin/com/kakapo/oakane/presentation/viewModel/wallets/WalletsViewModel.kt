package com.kakapo.oakane.presentation.viewModel.wallets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.oakane.common.asCustomResult
import com.kakapo.oakane.common.subscribe
import com.kakapo.oakane.data.repository.base.CategoryRepository
import com.kakapo.oakane.data.repository.base.WalletRepository
import com.kakapo.oakane.domain.usecase.selectedWalletUseCase
import com.kakapo.oakane.model.wallet.WalletItemModel
import com.kakapo.oakane.model.wallet.WalletModel
import com.kakapo.oakane.presentation.model.WalletSheetContent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WalletsViewModel(
    private val categoryRepository: CategoryRepository,
    private val walletRepository: WalletRepository
) : ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(WalletsState())

    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<WalletsEffect>()

    fun initializeData(){
        loadWallets()
        loadColors()
    }

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
            WalletsEvent.SaveWallet -> saveWallet()
            is WalletsEvent.SelectWalletBy -> selectWalletBy(event.id)
            is WalletsEvent.ClickedItem -> _uiState.update { it.onClickedItem(event.wallet) }
            WalletsEvent.DeleteDialog -> {}
            WalletsEvent.UpdateWallet -> {}
        }
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

    private fun loadColors() = viewModelScope.launch {
        val onSuccess: (List<String>) -> Unit = { colors ->
            _uiState.update { it.copy(colors = colors) }
        }
        categoryRepository.loadCategoryColors().asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun saveWallet() {
        val walletModel = uiState.value.toWalletModel()
        if (walletModel.id == 0L) add(walletModel)
        else update(walletModel)
    }

    private fun add(wallet: WalletModel) = viewModelScope.launch {
        walletRepository.save(wallet).fold(
            onSuccess = { _uiState.update { it.resetWalletsSheet() } },
            onFailure = ::handleError
        )
    }

    private fun update(wallet: WalletModel) = viewModelScope.launch {
        walletRepository.update(wallet).fold(
            onSuccess = { _uiState.update { it.resetWalletsSheet() } },
            onFailure = ::handleError
        )
        loadWallets()
    }

    private fun selectWalletBy(id: Long) = viewModelScope.launch {
        val wallets = uiState.value.wallets.selectedWalletUseCase(id)
        walletRepository.saveWallet(id).fold(
            onSuccess = { _uiState.update { it.copy(wallets = wallets) } },
            onFailure = ::handleError
        )
    }

    private fun showSheet(shown: Boolean) {
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