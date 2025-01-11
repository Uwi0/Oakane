package com.kakapo.oakane.presentation.viewModel.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.model.Currency
import com.kakapo.oakane.presentation.model.OnBoardingContent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val systemRepository: SystemRepository
) : ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(OnBoardingState())

    val uiEffect get() = _uiEffect
    private val _uiEffect = MutableSharedFlow<OnBoardingEffect>()

    fun handleEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.NavigateNext -> _uiState.update { it.copy(onBoardingContent = event.content) }
            is OnBoardingEvent.OnConfirmCurrency -> saveCurrency(event.currency)
            is OnBoardingEvent.ConfirmWallet -> {}
            OnBoardingEvent.SkippWallet -> {
                Logger.d("Skip_wallet")
                emit(OnBoardingEffect.NavigateToHome)
            }
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

    private fun emit(effect: OnBoardingEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}