package com.kakapo.oakane.presentation.viewModel.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.BackupRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.model.Currency
import com.kakapo.model.wallet.WalletModel
import com.kakapo.oakane.presentation.model.OnBoardingContent
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("OnBoardingViewModelKt")
class OnBoardingViewModel(
    private val systemRepository: SystemRepository,
    private val walletRepository: WalletRepository,
    private val backupRepository: BackupRepository
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(OnBoardingState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect
    private val _uiEffect = MutableSharedFlow<OnBoardingEffect>()

    fun handleEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.NavigateNext -> _uiState.update { it.copy(onBoardingContent = event.content) }
            is OnBoardingEvent.OnConfirmCurrency -> saveCurrency(event.currency)
            is OnBoardingEvent.ConfirmWallet -> createWallet(event.wallet)
            OnBoardingEvent.SkippWallet -> createDefaultWallet()
            OnBoardingEvent.OnclickRestoredBackup -> emit(OnBoardingEffect.RestoreBackup)
            is OnBoardingEvent.RestoreBackup -> restoreBackup(event.json)
        }
    }

    private fun saveCurrency(currency: Currency) = viewModelScope.launch {
        _uiState.update {
            it.copy(currency = currency, onBoardingContent = OnBoardingContent.CreateWallet)
        }
        systemRepository.saveCurrency(currency).fold(
            onSuccess = {},
            onFailure = {}
        )
    }

    private fun createDefaultWallet() = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            saveOnBoardingAlreadyRead()
        }
        walletRepository.createDefaultWallet().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun createWallet(wallet: WalletModel) = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            saveOnBoardingAlreadyRead()
        }
        walletRepository.save(wallet).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun restoreBackup(json: String) = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            emit(OnBoardingEffect.NavigateToHome)
        }
        backupRepository.restoreBackup(json).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun saveOnBoardingAlreadyRead() = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            emit(OnBoardingEffect.NavigateToHome)
        }
        systemRepository.saveOnBoardingAlreadyRead().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        emit(OnBoardingEffect.ShowError(throwable?.message ?: "Unknown error"))
    }

    private fun emit(effect: OnBoardingEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}