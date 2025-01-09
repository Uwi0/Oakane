package com.kakapo.oakane.presentation.viewModel.onboarding

import com.kakapo.model.Currency
import com.kakapo.oakane.presentation.model.OnBoardingContent

data class OnBoardingState(
    val onBoardingContent: OnBoardingContent = OnBoardingContent.Account,
    val currency: Currency = Currency.IDR,
)

sealed class OnBoardingEvent {
    data class NavigateNext(val content: OnBoardingContent): OnBoardingEvent()
    data class OnConfirmCurrency(val currency: Currency): OnBoardingEvent()
}