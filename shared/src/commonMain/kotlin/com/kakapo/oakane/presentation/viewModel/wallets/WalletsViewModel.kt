package com.kakapo.oakane.presentation.viewModel.wallets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.common.asCustomResult
import com.kakapo.common.subscribe
import com.kakapo.data.repository.base.CategoryRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.domain.usecase.selectedWalletUseCase
import com.kakapo.model.Currency
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.model.wallet.WalletModel
import com.kakapo.oakane.presentation.model.WalletSheetContent
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
    private val categoryRepository: CategoryRepository,
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
        loadColors()
        loadCurrency()
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
            is WalletsEvent.SelectPrimaryWalletBy -> selectWalletBy(event.id)
            is WalletsEvent.ClickedWallet -> emit(WalletsEffect.NavigateToWallet(event.item.id))
            is WalletsEvent.Dialog -> _uiState.update { it.copy(dialogShown = event.shown) }
            WalletsEvent.FeatureNotAvailable -> emit(WalletsEffect.ShowError("Feature not available yet"))
            WalletsEvent.ConfirmIcon -> _uiState.update { it.copy(sheetContent = WalletSheetContent.Create) }
            WalletsEvent.SaveWallet -> saveWallet()
            WalletsEvent.ConfirmDelete -> deleteWallet()
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

    private fun loadColors() = viewModelScope.launch {
        val onSuccess: (List<String>) -> Unit = { colors ->
            _uiState.update { it.copy(colors = colors) }
        }
        categoryRepository.loadCategoryColors().asCustomResult().subscribe(
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

    private fun saveWallet() {
        val walletModel = uiState.value.toWalletModel()
        if (walletModel.id == 0L) add(walletModel)
        else update(walletModel)
    }

    private fun add(wallet: WalletModel) = viewModelScope.launch {
        walletRepository.save(wallet).fold(
            onSuccess = ::handleWalletEvent,
            onFailure = ::handleError
        )
    }

    private fun update(wallet: WalletModel) = viewModelScope.launch {
        walletRepository.update(wallet).fold(
            onSuccess = ::handleWalletEvent,
            onFailure = ::handleError
        )
    }

    private fun deleteWallet() = viewModelScope.launch {
        val walletId = uiState.value.walletId
        walletRepository.deleteWalletBy(walletId).fold(
            onSuccess = ::handleWalletEvent,
            onFailure = ::handleError
        )
    }

    private fun handleWalletEvent(param: Unit){
        _uiState.update { it.resetWalletsSheet() }
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
        val isEditMode = uiState.value.walletId != 0L
        if (!shown) {
            if (isEditMode) _uiState.update { it.resetWalletsSheet() }
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