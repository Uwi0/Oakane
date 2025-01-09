package com.kakapo.oakane.presentation.viewModel.onboarding

import androidx.lifecycle.ViewModel
import com.kakapo.oakane.presentation.model.OnBoardingContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OnBoardingViewModel : ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(OnBoardingState())

    fun handleEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.NavigateNext -> _uiState.update { it.copy(onBoardingContent = event.content) }
            is OnBoardingEvent.OnConfirmCurrency -> _uiState.update { it.copy(currency = event.currency, onBoardingContent = OnBoardingContent.CreateWallet) }
        }
    }
}