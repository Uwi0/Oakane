package com.kakapo.oakane.presentation.viewModel.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.oakane.presentation.model.OnBoardingContent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnBoardingViewModel : ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(OnBoardingState())

    val uiEffect get() = _uiEffect
    private val _uiEffect = MutableSharedFlow<OnBoardingEffect>()

    fun handleEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.NavigateNext -> _uiState.update { it.copy(onBoardingContent = event.content) }
            is OnBoardingEvent.OnConfirmCurrency -> _uiState.update { it.copy(currency = event.currency, onBoardingContent = OnBoardingContent.CreateWallet) }
            is OnBoardingEvent.ConfirmWallet -> {}
            OnBoardingEvent.SkippWallet -> emit(OnBoardingEffect.NavigateToHome)
        }
    }

    private fun emit(effect: OnBoardingEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}